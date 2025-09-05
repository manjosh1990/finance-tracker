import {useState} from "react";
import {useNavigate} from "react-router-dom";
import Input from "../components/Input.jsx";

const Signup = () => {

    const [fullName, setFullName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [error, setError] = useState("");

// ... existing code ...
    const navigate = useNavigate();

    const validateSignup = ({ fullName, email, password, confirmPassword }) => {
        const name = fullName.trim();
        const mail = email.trim();

        const emailRe = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        const hasMinLen = password.length >= 8;
        const hasLetter = /[A-Za-z]/.test(password);
        const hasNumber = /\d/.test(password);

        if (!name) {
            return { error: "Please enter your full name." };
        }
        if (!mail || !emailRe.test(mail)) {
            return { error: "Please enter a valid email address." };
        }
        if (!hasMinLen) {
            return { error: "Password must be at least 8 characters long." };
        }
        if (!(hasLetter && hasNumber)) {
            return { error: "Password must include letters and numbers." };
        }
        if (password !== confirmPassword) {
            return { error: "Passwords do not match." };
        }

        return { error: null, data: { name, mail } };
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        const { error: validationError, data } = validateSignup({
            fullName,
            email,
            password,
            confirmPassword,
        });

        if (validationError) {
            return setError(validationError);
        }

        setError("");
        // Call your backend API here with data.name and data.mail (and password)
        // await api.signup({ name: data.name, email: data.mail, password });
        // navigate("/dashboard");
        console.log("Form valid. Submit here.", data);
    };

    return (
        <div className="min-h-svh w-full relative flex items-center justify-center overflow-hidden px-4 sm:px-6">
            {/*/!*Background Image*!/*/}
            {/*<img src="" alt="" className="absolute inset-0 w-full h-full object-cover filter blur-sm"/>*/}
            <div className="relative z-10 w-full max-w-lg px-0 sm:px-6">
                <div
                    className="rounded-xl sm:rounded-2xl p-[1.5px] sm:p-[2px] bg-brand-gradient shadow-xl sm:shadow-2xl sm:max-h-[90vh] sm:overflow-y-auto">
                    <div className="rounded-xl sm:rounded-2xl p-4 sm:p-8 bg-bg">
                        <h3 className="text-xl sm:text-2xl font-semibold text-center mb-2 text-gradient">
                            Create Account
                        </h3>
                        <p className="text-sm sm:text-base text-center mb-6 sm:mb-8 text-gradient">
                            Stay on top of your finances with our user-friendly platform.
                        </p>
                        <form className="space-y-4" onSubmit={handleSubmit}>
                            <div className="flex justify-center mb-4 sm:mb-6">
                                {/*profile picture*/}
                            </div>
                            <div className="grid grid-cols-1 sm:grid-cols-2 gap-3 sm:gap-4">
                                <Input
                                    value={fullName}
                                    onChange={(e) => setFullName(e.target.value)}
                                    label="Full Name"
                                    placeholder="Enter your full name"
                                    type="text"
                                    required
                                    autoComplete="name"
                                />
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
                                    <Input
                                        value={confirmPassword}
                                        onChange={(e) => setConfirmPassword(e.target.value)}
                                        label="Confirm Password"
                                        placeholder="Re-enter your password"
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
                                <button type="submit" className="btn-primary w-full py-3 sm:py-3.5 text-base sm:text-lg font-medium">
                                    Sign Up
                                </button>
                                <p className="text-sm sm:text-base text-gradient text-center mt-4 sm:mt-6">
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