import {authFetch} from "../../auth/fetchUser.js";
import moment from "moment";
import '../../styles/form.css';
import {useState} from "react";


export default function SubmitButton({ good, name, dateOfStart, onAddHabit, setActive, setName, setDateOfStart, setGood }) {
    const [isSubmitting, setIsSubmitting] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();

        if ( isSubmitting || !name.trim()) {
            return;
        }

        setIsSubmitting(true);

        const newHabit = {
            name,
            dateOfStart: moment().format("YYYY-MM-DD") + "T00:00:00",
            good,
        };

        // http://localhost:8080/auth/addBadHabit
        // http://localhost:8080/auth/addGoodHabit

        // https://vnikolaenko.site:8000/good-habit/create
        // https://vnikolaenko.site:8000/bad-habit/create

        try {
            const endpoint = good
                ? "https://vnikolaenko.site:8000/good-habit/create"
                : "https://vnikolaenko.site:8000/bad-habit/create";
            const response = await authFetch(endpoint, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(newHabit),
            });

            if (!response.ok) {
                console.log(response);
                return;
            }

            const savedHabit = await response.json();
            onAddHabit(savedHabit);

            setName("");
            setDateOfStart("");
            setGood(true);
            setActive(false);
        } catch (error) {
            console.log(error);
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <button onClick={handleSubmit} type="submit" disabled={isSubmitting}>
            {isSubmitting ? "Adding..." : "Add"}
        </button>
    );

}
