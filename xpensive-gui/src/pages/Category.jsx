import Dashboard from "../components/Dashboard.jsx";
import {useUser} from "../custom-hooks/useUser.jsx";

const Category = () => {
    useUser();
    return <Dashboard activeMenu="Category">
        This is category page
    </Dashboard>
}

export default Category;