import {API_ENDPOINTS} from "./apiEndpoints.js";

const CLOUDINARY_UPLOAD_PRESET = 'finance_tracker';

const uploadProfileImg = async (img) => {
    const formData = new FormData();
    formData.append('file', img);
    formData.append('upload_preset', CLOUDINARY_UPLOAD_PRESET);
    try {
       const res = await fetch(API_ENDPOINTS.UPLOAD_IMAGE, {
            method: 'POST',
            body: formData,
        })
        if(!res.ok){
            const error = await res.json();
            throw new Error(`Failed to upload image : ${error.error.message || res.statusText}`)
        }
        const data = await res.json();
        console.log("image uploaded successfully ",data)
        return data.secure_url;
    }catch (err){
        console.log("error while uploading image",err)
        throw err;
    }
};

export default uploadProfileImg;