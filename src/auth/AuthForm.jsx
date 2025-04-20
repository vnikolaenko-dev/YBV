import { useState } from "react";
import '../styles/auth.css';
import {useNavigate} from "react-router-dom";

export default function AuthForm() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);
    const [isLogin, setIsLogin] = useState(true);
    const navigate = useNavigate();

    // цветочек
    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);

        const endpoint = isLogin ? "http://localhost:8080/auth/login" : "http://localhost:8080/auth/register";
        const response = await fetch(endpoint, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });

        console.log(response.status);
        const data = await response.json();

        if (data.message && data.message.includes("already exists")) {
            setError("This email is already registered");
        } else if (data.status !== "OK") {
            setError(data.status);
        } else {
            sessionStorage.setItem("jwtToken", data.token);
            localStorage.setItem("email", email);
            navigate("/main");
            console.log("srabotalo");
        }
    };

    return (
        <div className="container">
            <h2 className="header">{isLogin ? "Sign in" : "Create account"}</h2>
            <form className="auth" onSubmit={handleSubmit}>
                <input
                    className="auth-input"
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
                <input
                    className="auth-input"
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                {error && <p className="error">{"Wrong email or password"}</p>}
                <button type="submit" className="submit">Continue</button>
            </form>
            <a
                href="#"
                onClick={(e) => {
                    e.preventDefault();
                    setIsLogin(!isLogin);
                    setError(null);
                }}
                className="var-button"
            >
                {isLogin ? "Don't have an account? Sign up" : "Already have an account? Sign in"}
            </a>
        </div>
    );
}
