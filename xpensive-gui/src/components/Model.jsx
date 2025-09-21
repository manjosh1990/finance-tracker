import {X} from "lucide-react";

const Model = ({isOpen, onClose, children, title}) => {
    if (!isOpen) {
        return null;
    }
    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center w-full h-full
        overflow-hidden bg-brand-purple/10 backdrop-blur-sm">
            <div className="relative p-4 w-full max-w-2xl max-h-[90vh=]">
                {/*Model header*/}
                <div className="relative bg-[#1a0033] rounded-xl border border-brand-pink">
                    {/*Model content*/}
                    <div className="flex items-center justify-between py-5 md:p-6 border-b border-brand-pink/40
                    rounded-t-xl">
                        <h3 className="text-xl font-semibold text-brand-pink">
                            {title}
                        </h3>
                        <button
                            onClick={onClose}
                            type="button"
                            className="text-brand-pink bg-brand-purple/10 hover:bg-brand-purple/30
                         hover:text-brand-pink rounded-lg text-sm w-9 h-9 inline-flex items-center justify-center transition-colors
                         duration-200 cursor-pointer focus:outline-none focus:ring-2 focus:ring-brand-pink/60"
                        >
                            <X className="w-4 h-4"  size={15}/>
                        </button>
                    </div>
                    {/*Model body*/}
                    <div className="p-5 md:p-6 text-brand-pink">
                        {children}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Model;