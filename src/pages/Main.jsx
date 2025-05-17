import { useState } from "react";
import useHabits from "../pages/main_components/useHabit.js";
import Account from "../account/Account.jsx";
import PopUpForm from "../habit/adding/PopUpForm.jsx";
import HabitGrid from "../pages/main_components/HabitGrid.jsx";
import "../styles/main.css";
import {authFetch} from "../auth/fetchUser.js";

export default function Main() {
    const [popUpActive, setPopUpActive] = useState(false);
    const [accInfo, setAccInfo] = useState(false);
    const [habits, setHabits] = useHabits();

    async function removeHabit(habitId) {
        const habitToRemove = habits.find(h => h.id === habitId);
        if (!habitToRemove) return;

        // http://localhost:8080/auth/${habitId}
        // https://vnikolaenko.site:8000/good-habit/remove/${habitId}
        // https://vnikolaenko.site:8000/bad-habit/remove/${habitId}

        const endpoint = habits.good
            ? `https://vnikolaenko.site:8000/good-habit/remove/${habitId}`
            : `https://vnikolaenko.site:8000/bad-habit/remove/${habitId}`;

        try {
            await authFetch(endpoint, {
                method: "DELETE",
            });
            setHabits(prev => prev.filter(h => h.id !== habitId));
        } catch (err) {
            console.error(err);
        }
    }

    async function breakDown(habitId) {
        try {
            // http://localhost:8080/auth/breakdown/${habitId}
            // https://vnikolaenko.site:8000/bad-habit/breakdown-now/${habitId}

            await authFetch(`https://vnikolaenko.site:8000/bad-habit/breakdown-now/${habitId}`, {
                method: "GET",
            });
        } catch (err) {
            console.error(err);
        }
    }

    return (
        <>
            <button className="account" onClick={() => setAccInfo(true)}>A</button>
            <Account active={accInfo} setActive={setAccInfo}/>

            <PopUpForm
                active={popUpActive}
                setActive={setPopUpActive}
                onAddHabit={newHabit => setHabits(prev => [...prev, newHabit])}
            />

            <HabitGrid
                habits={habits}
                removeHabit={removeHabit}
                breakDown={breakDown}
                onAddClick={() => setPopUpActive(true)}
                isFormOpen={popUpActive}
            />
        </>
    );
}

