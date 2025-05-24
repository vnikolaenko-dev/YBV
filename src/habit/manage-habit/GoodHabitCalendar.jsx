import { useState } from "react";

const daysOfWeek = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];

export default function GoodHabitCalendar({ habit, streak, setStreak, onUpdate }) {
    const [checkedDays, setCheckedDays] = useState(habit.checkedDays || Array(7).fill(false));

    const toggleDay = (index) => {
        const updated = [...checkedDays];
        updated[index] = !updated[index];
        setCheckedDays(updated);

        const newStreak = updated.filter(Boolean).length;
        setStreak(newStreak);

        // Обновление habit
        if (onUpdate) {
            onUpdate({
                ...habit,
                checkedDays: updated,
                streak: newStreak,
            });
        }
    };

    return (
        <div className="habit-calendar">
            {daysOfWeek.map((day, index) => (
                <div
                    key={index}
                    className={`calendar-dot-wrapper ${checkedDays[index] ? "checked" : ""}`}
                    onClick={() => toggleDay(index)}
                >
                    <div className="calendar-dot" />
                    <span>{day}</span>
                </div>
            ))}
        </div>
    );
}