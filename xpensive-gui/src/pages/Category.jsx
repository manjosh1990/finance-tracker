import Dashboard from "../components/Dashboard.jsx";
import {useUser} from "../custom-hooks/useUser.jsx";
import {Plus} from "lucide-react";
import CategoryList from "../components/CategoryList.jsx";
import {useEffect, useState} from "react";
import axiosConfig from "../util/axiosConfig.js";
import {API_ENDPOINTS} from "../util/apiEndpoints.js";
import toast from "react-hot-toast";
import Model from "../components/Model.jsx";
import AddCategoryForm from "../components/AddCategoryForm.jsx";

const Category = () => {
    useUser();
    const [loading, setLoading] = useState(false);
    const [categories, setCategories] = useState([]);
    const [showAddCategoryModal, setShowAddCategoryModal] = useState(false);
    const [showEditCategoryModal, setShowEditCategoryModal] = useState(false);
    const [selectedCategory, setSelectedCategory] = useState(null);

    const handleAddCategory = async (category) => {
        const {name, type, icon} = category;
        if (!name.trim()) {
            toast.error("Category name is required")
            return
        }
        const isCategoryPresent = categories.some((category) => {
            return category.name.toLowerCase() === name.toLowerCase();
        })
        if (isCategoryPresent) {
            toast.error("Category already exists")
            return
        }
        try {
            const res = await axiosConfig.post(API_ENDPOINTS.ADD_CATEGORIES, {name, type, icon})
            if (res.status === 201) {
                toast.success("Category added successfully");
                await fetchCategories();
                setShowAddCategoryModal(false);
            }
        } catch (error) {
            console.log("error while adding category", error);
            toast.error(error.response?.data?.detail || "error while adding category")
        }
    }

    const handleEditCategory = (category) => {
        console.log("category to edit", category)
        setSelectedCategory(category)
        setShowEditCategoryModal(true)
    }
    const handleUpdateCategory = async (category) => {
        console.log("category to update", category)
        const {id, name, type, icon} = category;
        if (!name.trim()) {
            toast.error("Category name is required")
            return
        }
        if (!id) {
            toast.error("Category id is required")
            return
        }
        try {
            const res = await axiosConfig.put(API_ENDPOINTS.UPDATE_CATEGORIES, {id, name, type, icon})
            if (res.status === 200) {
                toast.success("Category updated successfully");
                await fetchCategories();
                setSelectedCategory(null)
                setShowEditCategoryModal(false);
            }
        } catch (error) {
            console.log("error while updating category", error);
            toast.error(error.response?.data?.detail || "error while updating category")
        }
    }
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
    }, [])

    return (<Dashboard activeMenu="Category">
        <div className="my-5 mx-auto">
            {/*button to add category*/}
            <div className="flex justify-between items-center mb-5">
                <h2 className="text-2xl font-semibold text-brand-pink">
                    All Categories
                </h2>
                <button
                    onClick={() => setShowAddCategoryModal(true)}
                    className="add-btn flex items-center gap-1 text-sm text-brand-pink">
                    <Plus size={15} className="text-brand-pink"/>
                    Add Category
                </button>
            </div>
            {/*category list*/}
            <CategoryList categories={categories} onEditCategory={handleEditCategory}/>
            {/*adding category modal*/}
            <Model
                title="Add Category"
                isOpen={showAddCategoryModal}
                onClose={() => setShowAddCategoryModal(false)}
            >
                <AddCategoryForm onAddCategory={handleAddCategory}/>
            </Model>
            <Model
                title="Edit Category"
                isOpen={showEditCategoryModal}
                onClose={() => {
                    setShowEditCategoryModal(false)
                    setSelectedCategory(null)
                }}
            >
                <AddCategoryForm
                    onAddCategory={handleUpdateCategory}
                    isEditing={true}
                    intialCategoryData={selectedCategory}
                />
            </Model>
        </div>
    </Dashboard>)
}
export default Category;