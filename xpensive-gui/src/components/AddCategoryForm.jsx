import {useState} from "react";
import Input from "./Input.jsx";

const AddCategoryForm = () => {

    const [category, setCategory] = useState({
        name: "",
        type: "income",
        icon:""
    })

    const categoryTypes = [
        {value: "income", label: "Income"},
        {value: "expense", label: "Expense"},
    ]

    const handleChange = (key, value) => {
        setCategory({...category, [key]: value})
    }
    return(
        <div className="p-4">
            <Input
            value={category.name}
            onChange={(target) => handleChange("name",target.value)}
            label="Category Name"
            placeholder="e.g. Salary, Food, Transportation, etc."
            type="text"
            />
            <Input
                label="Category Type"
                value={category.type}
                onChange={(target) => handleChange("type",target.value)}
                isSelect={true}
                options={categoryTypes}
            />
        </div>
    )
}
export default AddCategoryForm;