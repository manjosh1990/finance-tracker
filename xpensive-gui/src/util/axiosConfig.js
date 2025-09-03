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
    if (error.response) {
        if (error.response.status === 401) {
            window.location.href = "/login";
        } else if (error.response.status === 500) {
            console.log("Internal server error");
        }
    } else if (error.code === "ECONNABORTED" || error.message === "Network Error") {
        console.log("Network Error: Please check your internet connection or try again later.");
    }
    return Promise.reject(error);
})
