import {useEffect, useState} from "react";
import {prepareIncomeLineChartData} from "../util/util.js";
import CustomLineChart from "./CustomLineChart.jsx";

const IncomeOverview = ({transactions}) => {
    const [chartData, setChartData] = useState([]);

    useEffect(() => {
        console.log("IncomeOverview useEffect", transactions);
        if (transactions && transactions.length > 0) {
            try {
                const result = prepareIncomeLineChartData(transactions);
                console.log("Chart data prepared:", result);
                setChartData(result);
            } catch (error) {
                console.error("Error preparing chart data:", error);
            }
        } else {
            console.log("No transactions data available");
            setChartData([]);
        }
        return () => {};
    }, [transactions]);

    return(
        <div className="card p-4 mb-4 border border-brand-purple/40 rounded-lg shadow-sm hover:shadow-md
        transition-shadow">
            <div className="flex items-center justify-between">
                <div>
                    <h5 className="text-xl font-bold bg-gradient-to-r from-brand-pink to-brand-purple bg-clip-text text-transparent">Income Overview</h5>
                    <p className="text-xs text-brand-pink m-0 5">
                        Track your earnings and analyze the trends.
                    </p>
                </div>
                <div className="mt-10 w-full">
                    {chartData && chartData.length > 0 ? (
                        <CustomLineChart data={chartData} />
                    ) : (
                        <div className="text-center text-gray-500 italic">No income data to display</div>
                    )}
                </div>
            </div>
        </div>
    )
}

export default IncomeOverview