import MenuBar from "./MenuBar.jsx";
import Sidebar from "./Sidebar.jsx";

const Dashboard = ({children}) => {
    return (
        <div>
            <MenuBar/>
            <div className="flex">
                <div className="max-[1080px]:hidden">
                    {/*sidebar*/}
                    <Sidebar/>
                </div>
                <div className="grow mx-5">
                    {children}
                </div>
            </div>
        </div>
    )
}
export default Dashboard;