import HabitCard from "./HabitCard.jsx";
import '../../styles/habit.css';

export default function HabitGrid({ habits, removeHabit, breakDown, onAddClick, isFormOpen }) {
    return (
        <div className="habit-grid-container">
            {habits.slice(0).reverse().map((habit, index) => (
                <HabitCard
                    key={index}
                    habit={habit}
                    removeHabit={removeHabit}
                    breakDown={breakDown}
                />
            ))}

            <div className={`add-habit ${isFormOpen ? "hidden" : "active"}`}
                 onClick={onAddClick}
            >
                <span>+</span>
            </div>

        </div>
    );

}
