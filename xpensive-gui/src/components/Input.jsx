const Input = ({label,value,onChange,placeholder,type}) => {
    return(
        <div className="mb-4">
            <label className="text-[13px] text-gradient block mb-1">
                { label}
            </label>
            <div className="relative">
                <input
                    type={type}
                    value={value}
                    onChange={(e)=>onChange(e)}
                    placeholder={placeholder}
                    className="w-full bg-transparent text-ink placeholder-ink/60 placeholder:text-xs border border-brand-purple/40 rounded-md py-2 px-3 pr-10 leading-tight focus:outline-none focus:border-brand-pink focus:ring-2 focus:ring-brand-pink/60"
                />
            </div>
        </div>
    )
}
export default Input;