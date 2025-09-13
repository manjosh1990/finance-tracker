import {Layers2, Pencil} from "lucide-react";

const CategoryList = ({categories, onEditCategory, OnDeleteCategory}) => {
    return (
        <div className="card p-4">
            <div className="flex items-center justify-between mb-4">
                <h4 className="text-lg font-semibold">
                    Category Sources
                </h4>
            </div>
            {/*category list*/}
            {
                categories && (
                    categories.length === 0 ? (
                        <p className="text-brand-pink">No Categories available, add to get started</p>
                    ) : (
                        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
                            {categories.map((category, index) => (
                                <div key={index}
                                     className="group relative flex items-center gap-4 p-3 rounded-lg hover:bg-brand-purple/20">
                                    <div className="w-12 h-12 flex items-center justify-center text-xl text-brand-pink rounded-full">
                                        {category.icon ? (
                                            <span className="text-2xl">
                                                    <img src={category.icon} alt="category.name" className="h-5 w-5"/>
                                                </span>
                                        ):(
                                            <Layers2 className="w-5 h-5 text-brand-pink" size={24}/>
                                        )}
                                    </div>
                                    <div className="flex-1 flex items-center justify-between">
                                        <div>
                                            <p className="text-sm text-brand-pink font-medium">
                                                {category.name}
                                            </p>
                                            <p className="text-sm text-brand-purple mt-1 capitalize">
                                                {category.type}
                                            </p>
                                        </div>
                                        <div className="flex items-center gap-2">
                                            <button className="text-brand-pink hover:text-blue-500
                                            opacity-0 group-hover:opacity-100
                                            transition-opacity cursor-pointer">
                                                <Pencil size={18}/>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            ))}
                        </div>
                    )
                )
            }
        </div>
    )
}
export default CategoryList;