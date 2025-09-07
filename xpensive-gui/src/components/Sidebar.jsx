import {useContext} from "react";
import {AppContext} from "../context/AppContext.jsx";
import {User} from "lucide-react";
import {SIDEBAR_DATA} from "../assets/assets.js";
import {useNavigate} from "react-router-dom";

const Sidebar = () => {
    const {user} = useContext(AppContext);
    const navigate = useNavigate();

    return (
        <div className="w-64 h-[calc(100vh-61px)] bg-[var(--color-bg)] sticky top-[61px] z-20">
            <div className="flex flex-col items-center justify-center gap-3 mt-0 mb-7">
                {user?.profileImageUrl ? (
                    <img
                        src={user.profileImageUrl}
                        alt="profile"
                        className="w-18 h-18 rounded-full object-cover object-center"
                    />
                ) : (
                    <User className="w-18 h-18 text-brand-pink text-xl"/>
                )}
                <h5 className="text-brand-pink font-medium leading-6">{user?.fullName
                    ? user.fullName
                        .trim()
                        .split(/\s+/)
                        .map(w => w.charAt(0).toUpperCase() + w.slice(1).toLowerCase())
                        .join(" ")
                    : "user"}
                </h5>
                {/*list of links*/}
                {
                    SIDEBAR_DATA.map((item, index) => (
                        <button
                            onClick={() => navigate(item.path)}
                            key={index} className="flex items-center gap-4 w-full text-[15px] text-brand-pink
                         py-3 px-8 rounded-lg hover:bg-brand-purple/20 cursor-pointer">
                            <item.icon className="text-xl "/>
                           <span className="flex justify-items-center pl-5">{item.title}</span>
                        </button>
                    ))
                }
            </div>
        </div>
    )
}
export default Sidebar;