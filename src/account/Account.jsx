import {useEffect, useState} from "react";
import '../styles/account.css';

export default function Account({ active, setActive }) {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");

    useEffect(() => {
        const savedName = localStorage.getItem("name");
        if (savedName) {
            setName(savedName);
        }
    }, []);

    useEffect(() => {
        const savedEmail = localStorage.getItem("email");
        if (savedEmail) {
            setEmail(savedEmail);
        }
    }, []);

    async function changeName(name) {
        const response = await fetch(`http://localhost:8080/auth/change-name/${name}`, {
            method: "GET",
        });
        console.log(name)
        if (response.status === 200) {
            alert("success")
        }
    }

    async function changeEmail(email) {
        const response = await fetch(`http://localhost:8080/auth/change-email/${email}`, {
            method: "GET",
        });
        console.log(email)
        if (response.status === 200) {
            alert("success")
        }
    }

    return (
        <div className={active ? "pop-up-block active" : "pop-up-block"} onClick={() => setActive(false)}>
            <div className={active ? "account-inf active" : "account-inf"} onClick={e => e.stopPropagation()}>
                <label className="account-inf-label">
                    Name:
                    <input
                        type="text"
                        value={name}
                        placeholder="Name"
                        required
                        onChange={(e) => setName(e.target.value)}
                    />
                </label>
                <button onClick={() => changeName(name)} type="submit">Change</button>
                <label className="account-inf-label">
                    Email:
                    <input
                        type="text"
                        value={email}
                        required
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </label>
                <button onClick={() => changeEmail(email)} type="submit">Change</button>
            </div>
        </div>
    )
}