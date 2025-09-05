import {useState} from "react";
import Input from "../components/Input.jsx";

const Login = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();

    }

    return (
        <div className="min-h-svh w-full relative flex items-center justify-center overflow-hidden px-4 sm:px-6">
            {/*/!*Background Image*!/*/}
            {/*<img src="" alt="" className="absolute inset-0 w-full h-full object-cover filter blur-sm"/>*/}
            <div className="relative z-10 w-full max-w-lg px-0 sm:px-6">
                <div
                    className="rounded-xl sm:rounded-2xl p-[1.5px] sm:p-[2px] bg-brand-gradient shadow-xl sm:shadow-2xl sm:max-h-[90vh] sm:overflow-y-auto">
                    <div className="rounded-xl sm:rounded-2xl p-4 sm:p-8 bg-bg">
                        <h3 className="text-xl sm:text-2xl font-semibold text-center mb-2 text-gradient">
  Sign in to your account
</h3>
<p className="text-sm sm:text-base text-center mb-6 sm:mb-8 text-gradient">
  Continue where you left off and stay in control of your finances.
</p>
                        <form className="space-y-4" onSubmit={handleSubmit}>
                            <div className="flex justify-center mb-4 sm:mb-6">
                                {/*profile picture*/}
                            </div>
                            <div className="sm:col-span-2 space-y-4">
                                <Input
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                    label="Email"
                                    placeholder="Enter your email"
                                    type="email"
                                    required
                                    autoComplete="email"
                                />
                                <div className="sm:col-span-2 space-y-4">
                                    <Input
                                        value={password}
                                        onChange={(e) => setPassword(e.target.value)}
                                        label="Password"
                                        placeholder="Enter your password"
                                        type="password"
                                        required
                                        minLength={8}
                                        autoComplete="new-password"
                                    />
                                </div>
                            </div>
                            <div>
                                {error && (
                                    <p className="text-red-800 text-sm text-center bg-red-50 p-2 mb-2 rounded">{error}</p>
                                )}
                                <button type="submit"
                                        className="btn-primary w-full py-3 sm:py-3.5 text-base sm:text-lg font-medium">
                                    LOGIN
                                </button>
                                <p className="text-sm sm:text-base text-gradient text-center mt-4 sm:mt-6">
                                    Don't have an account? <a href="/signup"
                                                              className="font-medium text-brand-pink hover:underline">SignUp</a>
                                </p>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Login