import {useRef, useState} from "react";
import {Trash, Upload, User} from "lucide-react";

const ProfilePhotoSelector = ({image, setImage}) => {
    const inputRef = useRef(null);
    const [previewUrl, setPreviewUrl] = useState(null);
    const handleImageChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            setImage(file);
            const preview = URL.createObjectURL(file);
            setPreviewUrl(preview);

        }
    }

    const handleRemoveImage = (e) => {
        e.preventDefault();
        setPreviewUrl(null);
        setImage(null);
    }

    const onChooseFile = (e) => {
        e.preventDefault();
        inputRef.current?.click();
    }
    return (
        <div className="flex justify-center mb-6">
            <input type="file"
                   name="" id=""
                   accept="image/*"
                   ref={inputRef}
                   onChange={handleImageChange}
                   className="hidden"
            />
            {!image ? (
                <div
                    className="relative w-20 h-20 rounded-full border-2 border-brand-purple/40 flex items-center justify-center cursor-pointer">
                    <User className="w-10 h-10 text-brand-purple" size={35}/>
                    <button
                        type="button"
                        aria-label="Upload profile photo"
                        className="w-8 h-8 flex items-center justify-center rounded-full bg-brand-purple shadow-sm ring-2 ring-brand-purple/40 absolute -bottom-1 -right-1"
                        onClick={onChooseFile}>
                        <Upload size={15}/>
                    </button>
                </div>) : (<>
                <div className="relative">
                    <img src={previewUrl} alt="profile" className="w-20 h-20 rounded-full object-cover"/>
                    <button
                        type="button"
                        aria-label="Remove profile photo"
                        onClick={handleRemoveImage}
                        className="w-8 h-8 flex items-center justify-center bg-red-800 text-white rounded-full absolute -bottom-1 -right-1"
                    >
                        <Trash size={15}/>
                    </button>
                </div>
            </>)}
        </div>
    )
}

export default ProfilePhotoSelector