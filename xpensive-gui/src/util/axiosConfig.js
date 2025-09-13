import axios from 'axios';
import {API_ENDPOINTS as apiEndpoints, BASE_URL} from "./apiEndpoints.js";

const axiosConfig = axios.create({
    baseURL: BASE_URL, headers: {
        "Content-Type": "application/json", Accept: "application/json",
    }
})

//dont require auth
const excludeEndpoint = ["/login", "/register", "/status", "/activate", "health"];
const REFRESH_PATH = apiEndpoints.REFRESH_TOKEN;
if (!excludeEndpoint.includes(REFRESH_PATH)) excludeEndpoint.push(REFRESH_PATH);

// Simple queue to hold requests while a refresh is in flight
let isRefreshing = false;
let failedQueue = [];

const processQueue = (error, token = null) => {
    failedQueue.forEach(p => {
        if (error) {
            p.reject(error);
        } else {
            p.resolve(token);
        }
    });
    failedQueue = [];
};

//request interceptor
axiosConfig.interceptors.request.use(config => {
    if (!excludeEndpoint.includes(config.url)) {
        const token = localStorage.getItem("token");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
    }
    return config;
}, error => {
    return Promise.reject(error);
})

//response interceptor
axiosConfig.interceptors.response.use(response => {
    return response;
}, async (error) => {
    const reqUrl = error.config?.url;
    const originalRequest = error.config;
    if (error.response) {
        console.log("Error response:", error.response);
        const status = error.response.status;

        // Avoid redirecting/refreshing for excluded endpoints like /login or /refresh
        const isExcluded = excludeEndpoint.includes(reqUrl);

        // ... existing code ...
        if (status === 401) {
            // If it's an excluded endpoint (e.g., /login or /refresh), let the caller handle and display the error
            if (isExcluded) {
                return Promise.reject(error);
            }

            // Prevent infinite retry loops
            if (originalRequest && originalRequest._retry) {
                // We've already retried this once, bail and redirect
                localStorage.removeItem("token");
                localStorage.removeItem("refreshToken");
                localStorage.removeItem("user");
                if (window.location.pathname !== "/login") {
                    window.location.href = "/login?session=expired";
                }
                return Promise.reject(error);
            }
            originalRequest._retry = true;

            const refreshToken = localStorage.getItem("refreshToken");
            if (!refreshToken) {
                // No refresh token available, force login
                localStorage.removeItem("token");
                localStorage.removeItem("user");
                if (window.location.pathname !== "/login") {
                    window.location.href = "/login?session=expired";
                }
                return Promise.reject(error);
            }

            if (isRefreshing) {
                // Queue the request until refresh finishes
                return new Promise((resolve, reject) => {
                    failedQueue.push({
                        resolve: (newToken) => {
                            if (originalRequest.headers) {
                                originalRequest.headers.Authorization = `Bearer ${newToken}`;
                            }
                            resolve(axiosConfig(originalRequest));
                        },
                        reject: (err) => reject(err),
                    });
                });
            }

            isRefreshing = true;
            try {
                // Use base axios to avoid interceptor recursion
                const refreshRes = await axios.post(
                    `${BASE_URL}${REFRESH_PATH}`,
                    { refreshToken },
                    {
                        headers: {
                            "Content-Type": "application/json",
                            Accept: "application/json",
                        },
                        timeout: 15000,
                    }
                );

                const { token: newAccessToken, refreshToken: newRefreshToken } = refreshRes.data || {};
                if (!newAccessToken) {
                    throw new Error("No access token returned from refresh.");
                }

                // Persist new tokens
                localStorage.setItem("token", newAccessToken);
                if (newRefreshToken) {
                    localStorage.setItem("refreshToken", newRefreshToken);
                }

                // Update default header for subsequent requests
                axiosConfig.defaults.headers.Authorization = `Bearer ${newAccessToken}`;

                // Resolve queued requests
                processQueue(null, newAccessToken);

                // Retry the original request
                if (originalRequest.headers) {
                    originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
                }
                return axiosConfig(originalRequest);
            } catch (refreshErr) {
                // Notify queued requests of failure
                processQueue(refreshErr, null);

                // Cleanup and redirect to login
                localStorage.removeItem("token");
                localStorage.removeItem("refreshToken");
                localStorage.removeItem("user");
                if (window.location.pathname !== "/login") {
                    window.location.href = "/login";
                }
                return Promise.reject(refreshErr);
            } finally {
                isRefreshing = false;
            }
        } else if (status === 500) {
            console.log("Internal server error");
        }
    } else if (error.code === "ECONNABORTED" || error.message === "Network Error") {
        console.log("Network Error: Please check your internet connection or try again later.");
    }
    return Promise.reject(error);
})

export default axiosConfig;