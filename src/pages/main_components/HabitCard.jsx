import { useState } from "react";
import HabitModal from "./HabitModal";
import "../../styles/habit.css";

export default function HabitCard({ habit, removeHabit, breakDown }) {
    const [active, setActive] = useState(false);

    return (
        <>
            <div className="habit-card" onClick={() => setActive(true)}>
                <div className="habit-header">
                    <span className={`habit-type ${habit.good ? 'good' : 'bad'}`}>
                        {habit.good ? 'Good' : 'Bad'}
                    </span>
                </div>
                <div className="habit-body">
                    <h3 className="habit-name">{habit.name}</h3>
                    <p className="habit-date">start at: {habit.dateOfStart.split("T")[0]}</p>
                </div>
            </div>

            <HabitModal
                active={active}
                setActive={setActive}
                habit={habit}
                removeHabit={removeHabit}
                breakDown={breakDown}
            />
        </>
    );

}
