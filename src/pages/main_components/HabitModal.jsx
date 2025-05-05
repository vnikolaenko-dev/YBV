import { useState } from "react";
import "../../styles/habit.css";

const daysOfWeek = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];

export default function HabitModal({ active, setActive, habit, removeHabit, breakDown }) {
    const [checkedDays, setCheckedDays] = useState(Array(7).fill(false));

    const toggleDay = (index) => {
        const updated = [...checkedDays];
        updated[index] = !updated[index];
        setCheckedDays(updated);
    };

    return (
        <div className={`habit-modal ${active ? "active" : ""}`} onClick={() => setActive(false)}>
            <div className="habit-modal-content" onClick={(e) => e.stopPropagation()}>
                <div className="habit-modal-header">
                    <div className="flame-icon">🔥</div>
                    <div>
                        <div className="habit-streak">{habit.streak || 0} days</div>
                        <div className="habit-subtitle">Habit streak</div>
                    </div>
                </div>

                <span className={`habit-type-modal ${habit.good ? 'good' : 'bad'}`}>
                        {habit.good ? 'Good' : 'Bad'}
                </span>
                <h2 className="habit-name-modal">{habit.name}</h2>
                <div className="habit-description-modal">
                    <p>
                        Start: {habit.dateOfStart.split("T")[0]}
                    </p>
                    <p className="habit-start-date">
                        Max score:
                    </p>
                    <p className="habit-start-date">
                        Score:
                    </p>
                </div>
                <div className="habit-calendar">
                    {daysOfWeek.map((day, index) => (
                        <div
                            key={index}
                            className={`calendar-dot-wrapper ${checkedDays[index] ? "checked" : ""}`}
                            onClick={() => toggleDay(index)}
                        >
                            <div className="calendar-dot"/>
                            <span>{day}</span>
                        </div>
                    ))}
                </div>

                <div className="habit-actions">
                    {!habit.good && (
                        <button className="habit-break" onClick={() => breakDown(habit.id)}>Break down</button>
                    )}
                    <button className="habit-delete" onClick={() => removeHabit(habit.id)}>Delete</button>
                </div>
            </div>
        </div>
    );

}
