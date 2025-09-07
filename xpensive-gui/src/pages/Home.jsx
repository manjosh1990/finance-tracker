import Dashboard from "../components/Dashboard.jsx";
import {useUser} from "../custom-hooks/useUser.jsx";

const Home = () =>{
    useUser();
    return (
        <div>
            <Dashboard activeMenu="Dashboard">
                This is home page
            </Dashboard>
        </div>
    )
}

export default Home