export const BASE_URL = "http://localhost:18080/api/v1.0"

const CLOUDINARY_NAME = "dfgel8gmi"

export const API_ENDPOINTS = {
    LOGIN: "/login",
    SIGNUP: "/register",
    UPLOAD_IMAGE: `https://api.cloudinary.com/v1_1/${CLOUDINARY_NAME}/image/upload`,
    GET_USER_INFO: "/profile",
    GET_ALL_CATEGORIES: "/categories",
    ADD_CATEGORIES: "/categories",
    UPDATE_CATEGORIES: "/categories",
    // UPDATE_CATEGORIES: (id) => `/categories/${id}`,
    REFRESH_TOKEN: "/refresh",
}