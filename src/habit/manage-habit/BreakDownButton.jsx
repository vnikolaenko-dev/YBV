import moment from "moment";
import { authFetch } from "../../auth/fetchUser.js";
import {useState} from "react";

export default function BreakDownButton({ habitId, onBreakDown }) {
    const [currentScore, setCurrentScore] = useState(0);
    const [maxScore, setMaxScore] = useState(0);

    const handleBreakDown = async () => {
        const time = moment().format("YYYY-MM-DD");

        try {
            const response = await authFetch(`https://vnikolaenko.site:8000/bad-habit/breakdown/${habitId}/${time}`, {
                method: 'GET',
            });

            console.log("Request URL:", `https://vnikolaenko.site:8000/bad-habit/breakdown-now/${habitId}/${time}`);
            console.log("Response status:", response.status);
            console.log("Response headers:", [...response.headers.entries()]);

            if (response.ok) {
                const data = await response.json();
                console.log("Breakdown response data:", data);
                onBreakDown(data);
            } else {
                console.error("Breakdown failed", response.status);
            }
        } catch (error) {
            console.error("Error during breakdown:", error);
        }
    };

    return (
        <button className="habit-break" onClick={handleBreakDown}>
            Break down
        </button>
    );
}
