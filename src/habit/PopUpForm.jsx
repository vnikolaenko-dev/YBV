import '../styles/form.css';
import { useState } from "react";
import moment from "moment";

export default function PopUpForm({ active, setActive, onAddHabit }) {
    const [dateOfStart, setDateOfStart] = useState("");
    const [name, setName] = useState("");
    const [good, setGood] = useState(true);
    const [target, setTarget] = useState(0);

    const handleSubmit = async (e) => {
        e.preventDefault();


        const newHabit = {
            name,
            dateOfStart: moment().format("YYYY-MM-DD"),
            target,
            good,
        };

        try {
            const endpoint = good ? "https://vnikolaenko.site:8000/good-habit/create" : "https://vnikolaenko.site:8000/bad-habit/create";
            const response = await fetch(endpoint, {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${sessionStorage.getItem("jwtToken")}`,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(newHabit),
            });

            if (!response.ok){
                console.log(response);
            }
            console.log(response);

            const savedHabit = await response.json();
            onAddHabit(savedHabit);

            setName("");
            setTarget(0);
            setGood(true);
            setActive(false);


        } catch (error) {
            console.log(error);
        }
    }

    return(
        <div className={active ? "pop-up-form active" : "pop-up-form"} onClick={() => setActive(false)}>
            <div className={active ? "habit-form active" : "habit-form"} onClick={e => e.stopPropagation()}>
                <div className="form_radio_btn">
                    <label className={`radio-label ${good ? "selected" : ""}`}>
                        <input
                            type="radio"
                            name="type"
                            value="good"
                            checked={good}
                            style={{
                                width: "50px",
                                height: "50px",
                            }}
                            onChange={() => setGood(true)}
                        />
                        Good Habit
                    </label>
                    <label className={`radio-label ${!good ? "selected" : ""}`}>
                        <input
                            type="radio"
                            name="type"
                            value="bad"
                            checked={!good}
                            style={{
                                width: "50px",
                                height: "50px",
                            }}
                            onChange={() => setGood(false)}
                        />
                        Bad Habit
                    </label>
                </div>
                <label>
                    Name:
                    <input
                        type="text"
                        value={name}
                        required
                        placeholder="Running"
                        onChange={(e) => setName(e.target.value)}
                    />
                </label>
                <label>
                    First goal (in weeks):
                    <input
                        type="number"
                        value={target}
                        min="1"
                        onChange={(e) => setTarget(Number(e.target.value))}
                    />
                </label>
                <button onClick={handleSubmit} type="submit">Add</button>
            </div>
        </div>
    )
}
