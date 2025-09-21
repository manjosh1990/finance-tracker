import {useState} from "react";
import {LoaderCircle} from "lucide-react";

const DeleteAlert = ({content, onDelete}) => {
    const [loading, setLoading] = useState(false);
    const handleDelete = async () => {
        try {
            setLoading(true);
            await onDelete();
        } finally {
            setLoading(false);
        }
    }
    return (
        <div>
            <p className="text-sm">
                {content}
            </p>
            <div className="flex justify-end gap-4 mt-6">
                <button
                    onClick={handleDelete}
                    type="button"
                    className="add-btn add-btn-fill"
                    disabled={loading}
                >
                    {loading ? (<>
                            <LoaderCircle className="animate-spin w-4 h-4"/>
                            Deleting..
                        </>
                    ) : (
                        <>Delete</>
                    )}
                </button>
            </div>
        </div>
    )
}
export default DeleteAlert;