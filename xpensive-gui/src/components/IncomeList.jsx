import {Download, Mail} from "lucide-react";
import TransactionInfoCard from "./TransactionInfoCard.jsx";
import moment from "moment";

const IncomeList = ({transactions, onDelete}) => {

    return (
        <div className="card p-4 mb-4 border border-brand-purple/40 rounded-lg shadow-sm hover:shadow-md
        transition-shadow ">
            <div className="flex items-center justify-between">
                <h5 className="text-lg text-brand-pink">Income Source</h5>
                <div className="flex items-center justify-end gap-2">
                    <button className="card-btn text-brand-pink bg-brand-purple/10
                    hover:bg-brand-purple/30 transition-colors cursor-pointer rounded px-1.5 py-0.25">
                        <Mail size={15} className="text-brand-pink"/>Send Email
                    </button>
                    <button className="card-btn text-brand-pink bg-brand-purple/10
                    hover:bg-brand-purple/30 transition-colors cursor-pointer rounded px-0.75 py-0.5">
                        <Download size={15} className="text-brand-pink"/> Download
                    </button>
                </div>
            </div>
            <div className="grid grid-cols-1 md:grid-cols-2">
                {/*display incomes*/}
                {transactions?.map((income, index) => (
                    <TransactionInfoCard key={index}
                                         icon={income.icon}
                                         title={income.name}
                                         date={moment(income.transactionDate).format("Do MMM, YYYY")}
                                         amount={income.amount}
                                         type="income"
                                         hideDeleteBtn={false}
                                         onDelete={() => onDelete(income.id)}
                    />
                ))}
            </div>
        </div>)
}
export default IncomeList;