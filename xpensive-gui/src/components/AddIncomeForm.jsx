import EmojiPickerPopup from "./EmojiPickerPopup.jsx";
import Input from "./Input.jsx";
import {useEffect, useState} from "react";
import {LoaderCircle} from "lucide-react";


const AddIncomeForm = ({categories, onAddIncome}) => {
    const [loading, setLoading] = useState(false);
    const [income, setIncome] = useState({
        name: "",
        amount: "",
        icon: "",
        categoryId: "",
        categoryName: "",
        transactionDate: ""
    });

    const handleSubmit = async (income) => {
        console.log(income)
        setLoading(true);
        try {
            await onAddIncome(income);
        } finally {
            setLoading(false);
        }
    }
    useEffect(() => {
        if(categories.length > 0 && !income.categoryId){
            setIncome((prev)=>({...prev, categoryId: categories[0].id}))
        }
    }, [categories, income, income.categoryId]);


    const categoryOptions = categories.map(category => ({
        value: category.id,
        label: category.name,
    }));

    const handleChange = (key, value) => {
        console.log(key, value)
        setIncome({...income, [key]: value});
    }
    return (
        <div>
            <EmojiPickerPopup icon={income.icon}
                              onSelect={(selectedIcon) => handleChange("icon", selectedIcon)}
            />
            <Input
                value={income.name}
                onChange={({target}) => handleChange("name", target.value)}
                label="Income Source"
                placeholder="e.g. Salary, Freelance, Bonus, etc."
                type="text"
            />
            <Input
                label="Category"
                value={income.categoryId}
                onChange={({target}) => handleChange("categoryId", target.value)}
                isSelect={true}
                options={categoryOptions}
            />
            <Input
                value={income.amount}
                onChange={({target}) => handleChange("amount", target.value)}
                label="Amount"
                placeholder="e.g. 10000.00, 20000.00, 30000.00, etc."
                type="number"/>

            <Input
                value={income.transactionDate}
                onChange={({target}) => handleChange("transactionDate", target.value)}
                label="Date"
                placeholder="e.g. 2024-01-01, 2024-02-01, 2024-03-01, etc."
                type="date"
                className="date-input-custom" // Add a custom class
            />

            <div className="flex justify-end mt-6">
                <button
                    type="button"
                    onClick={() => handleSubmit(income)}
                    className="add-btn add-btn-fill cursor-pointer
                     font-semibold py-2 px-4 rounded-md shadow-md hover:bg-brand-purple/40 transition-colors"
                    disabled={loading}
                >
                    {loading ? (
                        <>
                            <LoaderCircle className="animate-spin w-4 h-4"/>
                            Adding..
                        </>
                    ) : (<>
                        Add Income
                    </>)}
                </button>
            </div>
        </div>
    )
}
export default AddIncomeForm;