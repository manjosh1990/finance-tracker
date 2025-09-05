import {useState} from "react";
import {Eye, EyeOff} from "lucide-react";

const Input = ({label, value, onChange, placeholder, type}) => {

    const [showPassword, setShowPassword] = useState(false);

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    }
    return (
        <div className="mb-4">
            <label className="text-[13px] text-gradient block mb-1">
                {label}
            </label>
            <div className="relative">
                <input
                    type={type === "password" && showPassword ? "text" : type}
                    value={value}
                    onChange={(e) => onChange(e)}
                    placeholder={placeholder}
                    className="w-full bg-transparent text-ink placeholder-ink/60 placeholder:text-xs border border-brand-purple/40 rounded-md py-2 px-3 pr-10 leading-tight focus:outline-none focus:border-brand-pink focus:ring-2 focus:ring-brand-pink/60"
                />
                {type === "password" && (
                    <span className="absolute right-3 top-1/2 -translate-y-1/2 cursor-pointer">
                        {showPassword ? (
                            <Eye size={18} className="text-white/50 group-hover:text-white transition-colors
" onClick={togglePasswordVisibility}/>
                        ) : (
                            <EyeOff size={18} className="text-white/50 group-hover:text-white transition-colors
" onClick={togglePasswordVisibility}/>
                        )}
                    </span>
                )}
            </div>
        </div>
    )
}
export default Input;