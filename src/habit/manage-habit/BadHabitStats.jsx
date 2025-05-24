
export default function BadHabitStats({ streak }) {

    return (
        <>
            <div>
                <div className="habit-streak">{streak || 0} days</div>
                <div className="habit-subtitle">Habit streak</div>
            </div>
        </>
    );
}
