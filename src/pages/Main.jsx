import PopUpForm from "../habit/PopUpForm.jsx";
import '../styles/main.css';
import {useEffect, useState} from "react";
import Account from "../account/Account.jsx";

export default function Main() {
    const [popUpActive, setPopUpActive] = useState(false);
    const [accInfo, setAccInfo] = useState(false);
    const [habits, setHabits] = useState([]);
    const [habitsLoaded, setHabitsLoaded] = useState(false);

    useEffect(() => {
        const savedHabits = localStorage.getItem("habits");
        const habitsLoaded = localStorage.getItem("habitsLoaded");

        if (savedHabits && habitsLoaded === "true") {
            setHabits(JSON.parse(savedHabits));
        } else {
            fetch("http://localhost:8080/auth/bad-habit/get-all")
                .then(res => res.json())
                .then(habits => {
                    setHabits(habits);
                    localStorage.setItem("habits", JSON.stringify(habits));
                    localStorage.setItem("habitsLoaded", "true");
                })
            .catch(err => console.log(err));
        }
    }, []);

    useEffect(() => {
        localStorage.setItem("habits", JSON.stringify(habits));
    }, [habits]);

    async function removeHabit(habitId) {
        try {
            await fetch(`http://localhost:8080/auth/${habitId}`, {
                method: "DELETE",
            });
            setHabits((prev) => prev.filter(habit => habit.id !== habitId));
        } catch (error) {
            console.log(error);
        }
    }
    console.log(habits);

    async function breakDown(habitId) {
        try {
            await fetch(`http://localhost:8080/breakdown-now/${habitId}`, {
                method: "GET",
            });
        } catch (error) {
            console.log(error);
        }
    }

    return (
        <>
            <button className="account" onClick={() => setAccInfo(true)}>A</button>
            <Account
                active={accInfo}
                setActive={setAccInfo}
            />
            <button className="add-habit" onClick={() => setPopUpActive(true)}>+</button>
            <PopUpForm
                active={popUpActive}
                setActive={setPopUpActive}
                onAddHabit={(newHabit) => setHabits((prev) => [...prev, newHabit])}
            />
            <div className="habit-grid-container">
                {habits.map((habit, index) => (
                    <div className="habit" key={index}>
                        <button className="delete" onClick={() => removeHabit(habit.id)}>Nahuy</button>
                        <h3>{habit.name}</h3>
                        <p>Target: {habit.target} week(s)</p>
                        <p>Type: {habit.good ? "Good" : "Bad"}</p>

                        {/*где должна генерироваться дата?*/}
                        {/*{!habit.good && (*/}
                        {/*    <button className="breakdown" onClick={() => breakDown(habit.id)}>Break down</button>*/}
                        {/*)}*/}
                    </div>
                ))}
            </div>

        </>

    )
}
