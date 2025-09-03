
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Income from "./pages/Income.jsx";
import Expense from "./pages/Expense.jsx";
import Category from "./pages/Category.jsx";
import Login from "./pages/Login.jsx";
import Signup from "./pages/Signup.jsx";
import Filter from "./pages/Filter.jsx";
import Home from "./pages/Home.jsx";
import {Toaster} from "react-hot-toast";

const App = () =>{
    return(
       <BrowserRouter>
           <>
               <Toaster/>
           <Routes>
               <Route path="/dashboard" element={<Home/>}/>
               <Route path="/login" element={<Login/>}/>
               <Route path="/filter" element={<Filter/>}/>
               <Route path="/income" element={<Income/>}/>
               <Route path="/expense" element={<Expense/>}/>
               <Route path="/category" element={<Category/>}/>
               <Route path="/signup" element={<Signup/>}/>
           </Routes>
           </>
       </BrowserRouter>
    )
}

export default App