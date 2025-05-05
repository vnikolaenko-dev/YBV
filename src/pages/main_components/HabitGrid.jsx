import HabitCard from "./HabitCard.jsx";

export default function HabitGrid({ habits, removeHabit, breakDown }) {
    return (
        <div className="habit-grid-container">
            {habits.map((habit, index) => (
                <HabitCard
                    key={index}
                    habit={habit}
                    removeHabit={removeHabit}
                    breakDown={breakDown}
                />
            ))}
        </div>
    );
    //
}
