import {Area, AreaChart, CartesianGrid, ResponsiveContainer, Tooltip, XAxis, YAxis,} from 'recharts';
import {addThousandSeparator} from "../util/util.js";

const CustomLineChart = ({data}) => {
    const CustomTooltip = ({active, payload, label}) => {
        if (active && payload && payload.length) {
            const dataPoint = payload[0].payload;

            // Group items by category for the tooltip display
            const groupedItemsForTooltip = dataPoint.items.reduce((acc, item) => {
                const {categoryName, amount} = item;
                if (!acc[categoryName]) {
                    acc[categoryName] = {
                        categoryName: categoryName,
                        totalAmount: 0,
                    };
                }
                acc[categoryName].totalAmount += amount;
                return acc;
            }, {});

            // Convert grouped object to array for mapping
            const categoriesInTooltip = Object.values(groupedItemsForTooltip);

            return (
                <div className="bg-white/90 backdrop-blur-sm p-4 rounded-lg shadow-md border border-brand-purple/20">
                    {/* Display the formatted date at the top of the tooltip */}
                    <p className="text-sm font-semibold text-brand-purple mb-2">{label}</p>
                    <hr className="my-1 border-brand-purple/10"/>
                    {/* Display the total amount for the date */}
                    <p className="text-sm text-brand-pink font-bold mb-2">
                        Total: <span
                        className="text-brand-pink">&#8377;{addThousandSeparator(dataPoint.totalAmount)}</span>
                    </p>

                    {/* Iterate over the newly grouped categories for a consolidated view */}
                    {categoriesInTooltip && categoriesInTooltip.length > 0 && (
                        <div className="mt-2 pt-2 border-t border-brand-purple/10">
                            <p className="text-xs font-semibold text-brand-purple mb-1">Details:</p>
                            {categoriesInTooltip.map((groupedItem, index) => (
                                <div key={index} className="flex justify-between text-xs text-brand-pink">
                                    <span>{groupedItem.categoryName}:</span>
                                    <span>&#8377;{addThousandSeparator(groupedItem.totalAmount)}</span>
                                </div>
                            ))}
                        </div>
                    )}
                </div>
            );
        }
        return null;
    };

    return (
        <div className="rounded-lg">
            <ResponsiveContainer width="100%" height={300}>
                <AreaChart data={data}>
                    <defs>
                        <linearGradient id="incomeGradient" x1="0" y1="0" x2="0" y2="1">
                            <stop offset="5%" stopColor="#f472b6" stopOpacity={0.4}/>
                            <stop offset="95%" stopColor="#f472b6" stopOpacity={0.1}/>
                        </linearGradient>
                    </defs>

                    <CartesianGrid strokeDasharray="3 3" stroke="#c56cc5" opacity={0.5} vertical={false}/>
                    <XAxis
                        dataKey="month"
                        tick={{fontSize: 12, fill: "#c56cc5"}}
                        axisLine={false}
                        tickLine={false}
                    />
                    <YAxis
                        tick={{fontSize: 12, fill: "#c56cc5"}}
                        axisLine={false}
                        tickLine={false}
                        tickFormatter={(value) => `₹${value}`}
                    />
                    <Tooltip content={<CustomTooltip/>}/>

                    <Area
                        type="monotone"
                        dataKey="totalAmount"
                        stroke="#f472b6"
                        fill="url(#incomeGradient)"
                        strokeWidth={2}
                        activeDot={{r: 5, fill: "#f472b6", stroke: "#fff", strokeWidth: 2}}
                        dot={{r: 3, fill: "#f472b6", stroke: "#fff", strokeWidth: 1}}
                    />
                </AreaChart>
            </ResponsiveContainer>
        </div>
    );
};

export default CustomLineChart;