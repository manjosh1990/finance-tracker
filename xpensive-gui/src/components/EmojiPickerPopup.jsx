import {useState} from "react";
import {ImageIcon, X} from "lucide-react";
import EmojiPicker from "emoji-picker-react";

const EmojiPickerPopup = ({icon, onSelect}) => {
    const [isOpen, setIsOpen] = useState(false);
    const handleEmojiClick = (emoji) => {
        onSelect(emoji?.imageUrl || "")
        setIsOpen(false);
    }
    return (
        <div className="flex flex-col md:flex-row gap-5 items-start mb-6">
            <div
                onClick={() => setIsOpen(true)}
                className="flex items-center gap-4 cursor-pointer">
                <div className="w-12 h-12 flex items-center justify-center text-2xl
                text-brand-pink rounded-lg hover:bg-brand-purple/20">
                    {icon ? (
                        <img src={icon} alt="icon" className="w-12 h-12"/>
                    ) : (
                        <ImageIcon/>
                    )}

                </div>
                <p>
                    {icon ? "Change Icon" : "Pick Icon"}
                </p>
            </div>
            {isOpen && (
                <div className="relative">
                    <button
                        onClick={() => setIsOpen(false)}
                        className="w-7 h-7 flex items-center justify-center bg-brand-purple/10 border
                       border-brand-pink rounded-full absolute top-2 right-2 z-10 cursor-pointer">
                        <X/>
                    </button>
                    <div className="w-[92vw] sm:w-[90vw] md:w-[80vw] max-w-[420px] overflow-hidden z-20">
                        <EmojiPicker
                            className="p-2 rounded-2xl shadow-lg text-brand-pink w-full"
                            width="100%"
                            height={360}
                            style={{
                                // Theme surface + hover
                                "--epr-bg-color": "rgba(147, 51, 234, 0.12)",
                                "--epr-category-label-bg-color": "rgba(147, 51, 234, 0.18)",
                                "--epr-hover-bg-color": "rgba(147, 51, 234, 0.25)",

                                // Make text pink
                                "--epr-text-color": "currentColor",
                                "--epr-category-label-text-color": "currentColor",
                                "--epr-search-input-text-color": "currentColor",
                                "--epr-search-placeholder-color": "currentColor",

                                // Remove white borders
                                "--epr-picker-border-color": "transparent",
                                "--epr-search-input-border-color": "transparent",
                                "--epr-category-label-border-color": "transparent",
                                "--epr-preview-border-color": "transparent",

                                // Accents/icons in pink
                                "--epr-highlight-color": "currentColor",
                                "--epr-category-icon-active-color": "currentColor",
                                "--epr-category-icon-inactive-color": "currentColor",
                            }}
                            open={isOpen}
                            onEmojiClick={handleEmojiClick}/>
                    </div>

                </div>
            )}

        </div>
    )
}

export default EmojiPickerPopup;