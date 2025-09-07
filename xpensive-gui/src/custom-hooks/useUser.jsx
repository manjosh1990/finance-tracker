import {useContext, useEffect} from "react";
import {AppContext} from "../context/AppContext.jsx";
import {useNavigate} from "react-router-dom";
import axiosConfig from "../util/axiosConfig.js";
import {API_ENDPOINTS} from "../util/apiEndpoints.js";

export const useUser = () => {
    const {user,setUser,clearUser} = useContext(AppContext);
    const navigate = useNavigate();

    useEffect(()=>{
        if(user){
           return
        }
        let isMounted = true;

        const userInfo = async () => {
            try{
               const res =  await axiosConfig.get(API_ENDPOINTS.GET_USER_INFO);
               if(isMounted && res.data){
                   setUser(res.data);
               }
            }catch (err){
                console.log("error while fetching user info",err)
                if(isMounted){
                    clearUser();
                    navigate("/login");
                }
            }
        }
        userInfo();
        return ()=>{
            isMounted = false;
        }
    },[setUser,clearUser,user,navigate])
}