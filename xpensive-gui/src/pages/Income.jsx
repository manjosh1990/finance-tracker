import Dashboard from "../components/Dashboard.jsx";
import {useUser} from "../custom-hooks/useUser.jsx";
import {useEffect, useState} from "react";
import axiosConfig from "../util/axiosConfig.js";
import {API_ENDPOINTS} from "../util/apiEndpoints.js";
import toast from "react-hot-toast";
import IncomeList from "../components/IncomeList.jsx";
import Model from "../components/Model.jsx";
import {Plus} from "lucide-react";
import AddIncomeForm from "../components/AddIncomeForm.jsx";
import DeleteAlert from "../components/DeleteAlert.jsx";

const Income = () => {
    useUser();
    const [income, setIncome] = useState([]);
    const [categories, setCategories] = useState([])
    const [loading, setLoading] = useState(false);
    const [openAddIncomeModal, setOpenAddIncomeModal] = useState(false);
    const [openDeleteAlert, setOpenDeleteAlert] = useState({
        show: false,
        data: null
    });
    const fetchIncome = async () => {
        if (loading) return;
        setLoading(true);
        try {
            const res = await axiosConfig.get(API_ENDPOINTS.GET_ALL_INCOMES);
            if (res.status === 200) {
                console.log("income", res.data);
                setIncome(res.data);
            }
        } catch (error) {
            console.log("error while fetching incomes", error);
            toast.error(error.response?.data?.detail || "error while fetching incomes")
        } finally {
            setLoading(false);
        }
    }

    const fetchCategories = async () => {
        try {
            const res = await axiosConfig.get(API_ENDPOINTS.GET_CATEGORIES_BY_TYPE("income"));
            if (res.status === 200) {
                const incomeCategories = res.data;
                console.log("income categories", incomeCategories);
                setCategories(incomeCategories);
            }
        } catch (error) {
            console.log("error while fetching categories", error);
            toast.error(error.response?.data?.detail || "error while fetching categories")
        }
    }

    useEffect(() => {
        fetchIncome();
        fetchCategories();
    }, [])

    const onDelete = async (id) => {
        setOpenDeleteAlert({show: true, data: id})
    }

    //save income
    const handleAddIncome = async (income) => {
        console.log("income to add", income)
        const {amount, categoryId, transactionDate, icon, name} = income;
        if (!name.trim()) {
            toast.error("Name is required")
            return
        }
        if (!categoryId) {
            toast.error("Category is required")
            return
        }
        if (!amount.trim() || isNaN(amount) || Number(amount) <= 0) {
            toast.error("Please enter a valid amount")
            return
        }
        if (!transactionDate.trim()) {
            toast.error("Date is required")
            return
        }
        const today = new Date().toISOString().split('T')[0];
        if (transactionDate > today) {
            toast.error("Date cannot be in the future")
            return
        }
        try {
            const res = await axiosConfig.post(API_ENDPOINTS.ADD_INCOME, {amount, categoryId, transactionDate, icon, name})
            if (res.status === 201) {
                setOpenAddIncomeModal(false);
                toast.success("Income added successfully");
                await fetchIncome();
                await fetchCategories();
            }
        } catch (error) {
            console.log("error while adding income", error);
            toast.error(error.response?.data?.detail || "error while adding income")
        }
    }

    const handleDelete = async (id)=> {
        try{
            console.log("id to delete", id)
            const res = await axiosConfig.delete(API_ENDPOINTS.DELETE_INCOME(id));
            if (res.status === 204) {
                toast.success("Income deleted successfully");
                await fetchIncome();
                setOpenDeleteAlert({show: false, data: null});
            }
        }catch (error){
            console.log("error while deleting income", error);
            toast.error(error.response?.data?.detail || "error while deleting income")
        }finally {
        }
    }

    return <div>
        <Dashboard activeMenu="Income">
            <div className="my-5 mx-auto">
                <div className="grid grid-cols-1 gap-6">
                    <div>
                        {/*overview income with chart*/}
                        <button className="add-btn"
                                onClick={() => setOpenAddIncomeModal(true)}
                        >
                            <Plus size={15} className="text-brand-pink text-lg"/>Add Income
                        </button>
                    </div>
                    <IncomeList transactions={income} onDelete={onDelete}/>
                    {/*add income model*/}
                    <Model
                        title="Add Income"
                        isOpen={openAddIncomeModal}
                        onClose={() => setOpenAddIncomeModal(false)}
                    >
                        <AddIncomeForm categories={categories} onAddIncome={handleAddIncome}/>
                    </Model>
                    {/*delete income model*/}
                    <Model
                        isOpen={openDeleteAlert.show}
                        onClose={() => setOpenDeleteAlert({show: false, data: null})}
                        title="Delete Income"
                        >
                       <DeleteAlert content ="Are you sure you want to delete this income?"
                                    onDelete={()=>handleDelete(openDeleteAlert.data)}/>
                    </Model>
                </div>
            </div>
        </Dashboard>
    </div>
}
export default Income;