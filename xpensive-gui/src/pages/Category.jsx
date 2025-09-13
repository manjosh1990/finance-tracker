import Dashboard from "../components/Dashboard.jsx";
import {useUser} from "../custom-hooks/useUser.jsx";
import {Plus} from "lucide-react";
import CategoryList from "../components/CategoryList.jsx";
import {useEffect, useState} from "react";
import axiosConfig from "../util/axiosConfig.js";
import {API_ENDPOINTS} from "../util/apiEndpoints.js";
import toast from "react-hot-toast";

const Category = () => {
    useUser();
    const [loading, setLoading] = useState(false);
    const [categories, setCategories] = useState([]);
    const [showAddCategoryModal, setShowAddCategoryModal] = useState(false);
    const [showEditCategoryModal, setShowEditCategoryModal] = useState(false);
    const [selectedCategory, setSelectedCategory] = useState(null);

    const fetchCategories = async () => {
        if (loading) return;
        setLoading(true);
        try {
            const res = await axiosConfig.get(API_ENDPOINTS.GET_ALL_CATEGORIES);
            if (res.status === 200) {
                console.log("categories", res.data);
                setCategories(res.data);
            }
        } catch (error) {
            console.log("error while fetching categories", error);
            toast.error("error while fetching categories")
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        fetchCategories();
    },[])

        return (<Dashboard activeMenu="Category">
            <div className="my-5 mx-auto">
                {/*button to add category*/}
                <div className="flex justify-between items-center mb-5">
                    <h2 className="text-2xl font-semibold text-brand-pink">
                        All Categories
                    </h2>
                    <button className="add-btn flex items-center gap-1 text-sm text-brand-pink">
                        <Plus size={15} className="text-brand-pink"/>
                        Add Category
                    </button>
                </div>
                {/*category list*/}
                <CategoryList categories={categories}/>
                {/*adding category modal*/}
            </div>
        </Dashboard>)
}
export default Category;