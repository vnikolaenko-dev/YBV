import { useState } from "react";
import CreateHabitButton from "./CreateHabitButton.jsx";

export default function AddHabitForm({ setActive, onAddHabit }) {
    const [dateOfStart, setDateOfStart] = useState("");
    const [name, setName] = useState("");
    const [good, setGood] = useState(true);

    return (
        <>
            <div className="form_radio_btn">
                <label className={`radio-label ${good ? "selected" : ""}`}>
                    <input
                        type="radio"
                        name="type"
                        value="good"
                        checked={good}
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
                    placeholder={good ? "Running" : "Quite Smoking"}
                    onChange={(e) => setName(e.target.value)}
                />
            </label>
            <label>
                Date of Started:
                <input
                    type="date"
                    className="habit-date"
                    name="habit-start"
                    onChange={(e) => setDateOfStart(e.target.value)}
                />
            </label>
            <CreateHabitButton
                good={good}
                name={name}
                dateOfStart={dateOfStart}
                onAddHabit={onAddHabit}
                setActive={setActive}
                setName={setName}
                setDateOfStart={setDateOfStart}
                setGood={setGood}
            />

        </>
    );
}
