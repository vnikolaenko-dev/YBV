import "../../styles/habit.css";
import BadHabitStats from "./BadHabitStats.jsx";
import GoodHabitStats from "./GoodHabitStats.jsx";
import GoodHabitCalendar from "./GoodHabitCalendar.jsx";
import {useEffect, useState} from "react";
import BreakDownButton from "./BreakDownButton.jsx";
import moment from "moment/moment.js";

export default function HabitModal({ active, setActive, habit, habitData, removeHabit, onUpdate }) {
    const [streak, setStreak] = useState( 0);
    const [maxScore, setMaxScore] = useState(habitData?.maxScore ?? 0);

    useEffect(() => {
        if (habitData?.currentScore !== undefined && habitData?.maxScore !== undefined) {
            setStreak(habitData.currentScore);
            setMaxScore(habitData.maxScore);
        } else if (habit.dateOfStart) {
            const start = moment(habit.dateOfStart.split("T")[0]);
            const today = moment().startOf("day");
            const days = today.diff(start, "days") + 1;
            const validDays = days > 0 ? days : 0;

            setStreak(validDays);
            setMaxScore(validDays);
        }
    }, [habitData, habit.dateOfStart]);

    const handleBreakDown = (data) => {
        console.log("handleBreakDown called with data:", data);
        setStreak(data.currentScore);
        setMaxScore(data.maxScore);
        onUpdate({ ...habitData, currentScore: data.currentScore, maxScore: data.maxScore });
    };

    return (
        <div className={`habit-modal ${active ? "active" : ""}`} onClick={() => setActive(false)}>
            <div className="habit-modal-content" onClick={(e) => e.stopPropagation()}>
                <div className="habit-modal-header">
                    <div className="flame-icon">🔥</div>
                    {habit.good ? (
                        <GoodHabitStats habit={habit} streak={streak} />
                    ) : (
                        <BadHabitStats streak={streak} />
                    )}
                </div>

                <span className={`habit-type-modal ${habit.good ? 'good' : 'bad'}`}>
                    {habit.good ? 'Good' : 'Bad'}
                </span>
                <h2 className="habit-name-modal">{habit.name}</h2>

                <div className="habit-description-modal">
                    <p><strong>Start:</strong> {habit.dateOfStart.split("T")[0]}</p>
                    <p><strong>Max score:</strong> {maxScore ?? 0}</p>
                </div>

                {habit.good && (
                    <GoodHabitCalendar
                        habit={habit}
                        streak={streak}
                        setStreak={setStreak}
                        onUpdate={onUpdate}
                    />
                )}



                <div className="habit-actions">
                    {!habit.good && (<BreakDownButton habitId={habit.id}  onBreakDown={handleBreakDown} />)}
                    <button className="habit-delete" onClick={() => removeHabit(habit.id)}>Delete</button>
                </div>
            </div>
        </div>
    );
}