import {Trash, Trash2, TrendingDown, TrendingUp, UtensilsCrossed} from "lucide-react";
import {addThousandSeparator, formatIndianNumber} from "../util/util.js";

const TransactionInfoCard = ({icon,title,date,amount,type,hideDeleteBtn,onDelete}) => {
    const getAmountStyles = () =>{
        if(type === "income"){
            return "bg-green-50 text-green-800"
        }else{
            return "bg-red-50 text-red-800"
        }
    }

    return (
        <div className="group relative flex items-center gap-4
        p-3 mt-2 rounded-lg hover:bg-brand-purple/20">
            <div className="w-12 h-12 flex items-center justify-center text-xl text-brand-pink rounded-full">
                {icon ?(
                    <img src={icon} alt="icon" className="h-6 w-6"/>
                ):(
                    <UtensilsCrossed className="w-6 h-6 text-brand-pink"/>
                )}
            </div>
            <div className="flex-1 flex items-center justify-between">
                <div>
                    <p className="text-sm text-brand-pink font-medium">
                        {title}
                    </p>
                    <p className="text-xs text-brand-purple mt-1 capitalize">
                        {date}
                    </p>
                </div>
                <div className="flex items-center gap-2">
                    {!hideDeleteBtn && (
                        <button className="text-brand-pink hover:text-blue-500 opacity-0
                        group-hover:opacity-100 transition-opacity cursor-pointer"
                                onClick={onDelete}>
                            <Trash2 size={18} className="w-4 h-4"/>
                        </button>)
                    }
                    <div className= {`flex items-center gap-2 rounded-md px-3 py-1.5 ${getAmountStyles()}`}>
                        <h6 className="text-xs font-medium">
                            {type === "income" ? "+" : "-"} ₹{formatIndianNumber(amount)}
                        </h6>
                        {type === "income" ? (
                            <TrendingUp size={18} className="w-4 h-4"/>
                        ):(
                            <TrendingDown size={18} className="w-4 h-4"/>
                        )}
                    </div>
                </div>
            </div>
        </div>
    )
}
export default TransactionInfoCard;