import logo from './logo.jpg';
import logo1 from './logo1.png';
import logo2 from './xpensive-logo.png';
import {Coins, FunnelPlus, LayoutDashboard, List, Wallet} from "lucide-react";

export const assets = { logo,logo1,logo2 };

export const SIDEBAR_DATA = [
    {
        id : "01",
        title : "Dashboard",
        icon : LayoutDashboard,
        path : "/dashboard",
    },
    {
        id : "02",
        title : "Category",
        icon : List,
        path : "/category",
    },
    {
        id : "03",
        title : "Income",
        icon : Wallet,
        path : "/income",
    },
    {
        id : "04",
        title : "Expense",
        icon : Coins,
        path : "/expense",
    },
    {
        id : "05",
        title : "Filter",
        icon : FunnelPlus,
        path : "/filter",
    }
]