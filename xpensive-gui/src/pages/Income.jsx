import Dashboard from "../components/Dashboard.jsx";
import {useUser} from "../custom-hooks/useUser.jsx";

const Income = () => {
    useUser();
    return <div>
        <Dashboard activeMenu="Income">
            This is Income page
        </Dashboard>
    </div>
}
export default Income;