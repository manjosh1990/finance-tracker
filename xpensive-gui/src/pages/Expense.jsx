import Dashboard from "../components/Dashboard.jsx";
import {useUser} from "../custom-hooks/useUser.jsx";

const Expense = () => {
    useUser();
    return <div>
        <Dashboard activeMenu="Expense">
            This is Expense page
        </Dashboard>
    </div>
}
export default Expense;