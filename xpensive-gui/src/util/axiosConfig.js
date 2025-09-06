import axios from 'axios';
import {BASE_URL} from "./apiEndpoints.js";

const axiosConfig = axios.create({
    baseURL: BASE_URL, headers: {
        "Content-Type": "application/json", Accept: "application/json",
    }
})

//dont require auth
const excludeEndpoint = ["/login", "/register", "/status", "/activate", "health"];


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
}, (error) => {
    const reqUrl = error.config?.url;
    if (error.response) {
        console.log("Error response:", error.response);
        const status = error.response.status;

        // Avoid redirecting for excluded endpoints like /login so the UI can show the error message
        const isExcluded = excludeEndpoint.includes(reqUrl);

        if (status === 401) {
            if (!isExcluded && window.location.pathname !== "/login") {
                // Likely an expired/invalid session on a protected route
                window.location.href = "/login?session=expired";
            }
            // If it's an excluded endpoint (e.g., /login), let the caller handle and display the error
        } else if (status === 500) {
            console.log("Internal server error");
        }
    } else if (error.code === "ECONNABORTED" || error.message === "Network Error") {
        console.log("Network Error: Please check your internet connection or try again later.");
    }
    return Promise.reject(error);
})

export default axiosConfig;