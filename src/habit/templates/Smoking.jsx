import { useState } from "react";
import "../../styles/smoking.css";
import CreateHabitButton from "../adding/CreateHabitButton.jsx";

export default function Smoking({ setActive, onAddHabit, onBack }) {
    const [type, setType] = useState("cigarettes");
    const [dateOfStart, setDateOfStart] = useState("");
    const [name, setName] = useState("Quite Smoking");
    const [good, setGood] = useState(false);

    return (
        <div className="smoking-modal">
            <button className="close-modal" onClick={onBack}>‹</button>
            <h2 className="modal-title">🚭 Quit Smoking</h2>

            <label>
                Start date:
                <input
                    type="date"
                    className="modal-input"
                    onChange={(e) => setDateOfStart(e.target.value)}
                />
            </label>

            <label>
                What do you want to quit?
                <select
                    value={type}
                    onChange={(e) => setType(e.target.value)}
                    className="modal-input"
                >
                    <option value="cigarettes">Cigarettes</option>
                    <option value="vape">E-cigarettes</option>
                </select>
            </label>

            {type === "cigarettes" && (
                <div className="type-section">
                    <label>
                        Cigarettes per day:
                        <input type="number" className="modal-input" min="1"/>
                    </label>
                    <label>
                        Cigarettes per pack:
                        <input type="number" className="modal-input" min="1"/>
                    </label>
                    <label>
                        Cost per pack (₽):
                        <input type="number" className="modal-input" min="1"/>
                    </label>
                </div>
            )}

            {type === "vape" && (
                <div className="type-section">
                    <label>
                        Vapes per month:
                        <input type="number" className="modal-input" min="1"/>
                    </label>
                    <label>
                        Average cost per vape (₽):
                        <input type="number" className="modal-input" min="1"/>
                    </label>
                </div>
            )}

            <CreateHabitButton
                good={false}
                name={"Quite Smoking"}
                dateOfStart={dateOfStart}
                onAddHabit={onAddHabit}
                setActive={setActive}
                setName={setName}
                setDateOfStart={setDateOfStart}
                setGood={setGood}
            />


        </div>
    );

}
