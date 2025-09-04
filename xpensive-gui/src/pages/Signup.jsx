import {useState} from "react";
import {useNavigate} from "react-router-dom";
import Input from "../components/Input.jsx";

const Signup = () => {

    const [fullName, setFullName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [error, setError] = useState("");

    const navigate = useNavigate();
    return (
        <div className="h-screen w-full relative flex items-center justify-center overflow-hidden">
            {/*/!*Background Image*!/*/}
            {/*<img src="" alt="" className="absolute inset-0 w-full h-full object-cover filter blur-sm"/>*/}
            <div className="relative z-10 w-full max-w-lg px-6">
                <div
                    className="rounded-lg p-[2px] bg-brand-gradient shadow-2xl max-h-[90vh] overflow-y-auto">
                    <div className="rounded-lg p-8 bg-bg">
                        <h3 className="text-2xl font-semibold text-center mb-2 text-gradient">
                            Create Account
                        </h3>
                        <p className="text-sm text-center mb-8 text-gradient">
                            Stay on top of your finances with our user-friendly platform.
                        </p>
                        <form className="space-y-4 ">
                            <div className="flex justify-center mb-6">
                                {/*profile picture*/}
                            </div>
                            <div className="grid grid-cols-2 gap-4 md:grid-cols-2">
                                <Input
                                    value={fullName}
                                    onChange={(e) => setFullName(e.target.value)}
                                    label="Full Name"
                                    placeholder="Enter your full name"
                                    type="text"
                                />
                                <Input
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                    label="Email"
                                    placeholder="Enter your email"
                                    type="text"
                                />
                                <div className="col-span-2">
                                    <Input
                                        value={password}
                                        onChange={(e) => setPassword(e.target.value)}
                                        label="Password"
                                        placeholder="Enter your password"
                                        type="password"
                                    />
                                    <Input
                                        value={confirmPassword}
                                        onChange={(e) => setConfirmPassword(e.target.value)}
                                        label="Confirm Password"
                                        placeholder="re-enter your password"
                                        type="password"
                                    />
                                </div>
                            </div>
                            <div>
                                {error && (
                                    <p className="text-red-800 text-sm text-center bg-red-50 p-2 rounded">{error}</p>
                                )}
                                <button className="btn-primary w-full py-3 text-lg font-medium">Sign Up</button>
                                <p className="text-sm text-gradient text-center mt-6">
                                    Already have an account? <a href="/login" className="font-medium text-brand-pink hover:underline">Login</a>
                                </p>

                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    )


}

export default Signup