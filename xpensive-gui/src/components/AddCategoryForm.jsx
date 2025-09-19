import {useState} from "react";
import Input from "./Input.jsx";
import EmojiPickerPopup from "./EmojiPickerPopup.jsx";
import {LoaderCircle} from "lucide-react";

const AddCategoryForm = ({onAddCategory}) => {
    const [loading, setLoading] = useState(false);
    const [category, setCategory] = useState({
        name: "",
        type: "income",
        icon: ""
    })

    const categoryTypes = [
        {value: "income", label: "Income"},
        {value: "expense", label: "Expense"},
    ]

    const handleChange = (key, value) => {
        setCategory({...category, [key]: value})
    }

    const handleSubmit = async () => {
        setLoading(true);
        try {
            await onAddCategory(category);
        } finally {
            setLoading(false);
        }
    }
    return (
        <div className="p-4">
            <EmojiPickerPopup icon={category.icon}
                              onSelect={(icon) => handleChange("icon", icon)}
            />
            <Input
                value={category.name}
                onChange={({target}) => handleChange("name", target.value)}
                label="Category Name"
                placeholder="e.g. Salary, Food, Transportation, etc."
                type="text"
            />
            <Input
                label="Category Type"
                value={category.type}
                onChange={({target}) => handleChange("type", target.value)}
                isSelect={true}
                options={categoryTypes}
            />
            <div className="flex justify-end mt-6">
                <button
                    type="button"
                    onClick={handleSubmit}
                    disabled={loading}
                    className="add-btn add-btn-fill cursor-pointer
                     font-semibold py-2 px-4 rounded-md shadow-md hover:bg-brand-purple/40 transition-colors">
                    {loading ? (
                        <><LoaderCircle className="animate-spin w-4 h-4"/>
                            Adding..</>
                    ) : (
                        <>
                            Add Category
                        </>
                    )}
                </button>
            </div>
        </div>
    )
}
export default AddCategoryForm;