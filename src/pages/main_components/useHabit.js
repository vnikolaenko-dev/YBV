import { useEffect, useState } from "react";
import { authFetch } from "../../auth/fetchUser.js";

export default function useHabits() {
    const [habits, setHabits] = useState([]);
    const [habitsLoaded, setHabitsLoaded] = useState(false);

    useEffect(() => {
        const savedHabits = localStorage.getItem("habits");
        const habitsLoadedFlag = localStorage.getItem("habitsLoaded");

        if (savedHabits && habitsLoadedFlag === "true") {
            setHabits(JSON.parse(savedHabits));
        } else {
            // http://localhost:8080/auth/bad-habit/get-all
            // https://vnikolaenko.site:8000/habit/get-all
            authFetch("https://vnikolaenko.site:8000/habit/get-all", {
                method: "GET",
            })
                .then(res => res.json())
                .then(data => {
                    setHabits(data);
                    localStorage.setItem("habits", JSON.stringify(data));
                    localStorage.setItem("habitsLoaded", "true");
                })
                .catch(console.error);
        }
    }, []);

    useEffect(() => {
        localStorage.setItem("habits", JSON.stringify(habits));
    }, [habits]);

    return [habits, setHabits];

}
