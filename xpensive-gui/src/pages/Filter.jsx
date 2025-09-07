import Dashboard from "../components/Dashboard.jsx";
import {useUser} from "../custom-hooks/useUser.jsx";

const Filter = () => {
    useUser();
    return (
        <Dashboard activeMenu="Filter">
            This is filter page
        </Dashboard>
    )
}
export default Filter;