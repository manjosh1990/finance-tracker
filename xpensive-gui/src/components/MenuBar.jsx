import {useContext, useRef, useState} from "react";
import {AppContext} from "../context/AppContext.jsx";
import {useNavigate} from "react-router-dom";
import {LogOut, Menu, User, X} from "lucide-react";
import {assets} from "../assets/assets.js";

const MenuBar = () => {

    const [openSidebar, setOpenSidebar] = useState(false);
    const [showDropdown, setShowDropdown] = useState(false);
    const dropDownRef = useRef(null);
    const {user, clearUser} = useContext(AppContext);
    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.clear()
        clearUser();
        setShowDropdown(false)
        navigate("/login");
    }
    return (

        <div className="sticky top-0 z-30">
            <div
                className="relative flex items-center justify-between gap-5 bg-[var(--color-bg)] text-brand-pink shadow-md px-4 py-4 sm:px-7">
                {/**logo - Menu Icon and title*/}
                <div className="flex items-center gap-5">
                    <button
                        onClick={() => setOpenSidebar(!openSidebar)}
                        className="block lg:hidden text-brand-pink hover:bg-brand-purple/20 rounded-md p-2 cursor-pointer transition-colors focus:outline-none focus:ring-2 focus:ring-brand-pink/60 active:bg-brand-purple/30"
                        aria-label={openSidebar ? "Close menu" : "Open menu"}>
                        {openSidebar ? (
                            <X size={20} className="text-2xl text-brand-pink"/>
                        ) : (
                            <Menu size={20} className="text-2xl text-brand-pink"/>
                        )}
                    </button>
                    <div className="flex items-center gap-2">
                        <img src={assets.logo2} alt="logo" className="w-10 h-10 object-contain"/>
                        <span className="text-lg font-medium text-brand-pink pt-2">
                             Xpensive
                         </span>
                    </div>
                </div>
                {/**Right Side -> profile pic, notifications, settings*/}
                <div className="relative" ref={dropDownRef}>
                    <button
                        onClick={() => setShowDropdown(!showDropdown)}
                        className="flex items-center justify-center w-10 h-10
                         bg-brand-purple/10 hover:bg-brand-purple/30 rounded-full transition-colors focus:outline-none
                         duration-200 focus:ring-2 focus:ring-brand-pink/60 focus:ring-offset-2 focus:ring-offset-[var(--color-bg)]
                     ">
                        <User size={20} className="text-2xl text-brand-pink"/>
                    </button>
                    {/*Drop down menu*/}
                    {showDropdown && (
                        <div
                            className="absolute right-0 mt-2 w-48 rounded-xl p-[1px] bg-gradient-to-r from-brand-pink to-brand-purple z-50 shadow-lg">
                            <div className="bg-[var(--color-bg)] rounded-xl overflow-hidden py-1">
                                {/**Profile information*/}
                                <div
                                    className="px-4 py-3 border-b border-brand-purple/40 text-sm text-brand-pink font-medium text-center cursor-pointer">
                                    <div className="flex items-center gap-3">
                                        <div
                                            className="flex items-center justify-center w-8 h-8 bg-brand-purple/20 rounded-full">
                                            <User size={15} className="w-4 h-4 text-brand-pink"/>
                                        </div>
                                        <div className="flex-1 min-w-0">
                                            <p className="text-sm font-medium text-brand-pink truncate">
                                                {user?.fullName}
                                            </p>
                                            <p className="text-xs text-brand-purple/100 truncate">
                                                {user?.email}
                                            </p>
                                        </div>

                                    </div>
                                </div>
                                {/**Dropu down options    */}
                                <div className="py-1">
                                    <button
                                        onClick={handleLogout}
                                        className="flex items-center gap-3 w-full px-4 py-2 text-sm text-brand-pink
                                         hover:bg-brand-purple/20 transition-colors duration-150">
                                        <LogOut className="w-4 h-4 text-brand-pink"/>
                                        <span>Logout</span>
                                    </button>
                                </div>
                            </div>
                        </div>
                    )}

                </div>
                {/**Mobile view*/}
                <span>mobile</span>
                {/* bottom-only gradient line */}
                <div
                    className="pointer-events-none absolute inset-x-0 -bottom-px h-px bg-gradient-to-r from-brand-pink to-brand-purple/90"></div>
            </div>
        </div>
    )
}
export default MenuBar;