import '../../styles/form.css';
import { useState } from "react";
import AddHabitForm from './AddHabitForm.jsx';
import Templates from './AddHabitTemplate.jsx';
import Smoking from "../templates/Smoking.jsx";

export default function PopUpForm({ active, setActive, onAddHabit }) {
    const [tab, setTab] = useState("form");

    const handleTemplateSelect = (templateName) => {
        setTab(`template-${templateName}`);
    };

    const handleBack = () => {
        setTab("templates");
    };

    return (
        <>
            <div className={active ? "pop-up-form active" : "pop-up-form"} onClick={() => setActive(false)}>
                <div className={active ? "habit-form active" : "habit-form"} onClick={e => e.stopPropagation()}>
                    {!(tab.startsWith("template-")) && (
                        <div className="tab-header">
                            <label className={`tab ${tab === "form" ? "selected" : ""}`}>
                                <input
                                    type="radio"
                                    name="tab"
                                    value="form"
                                    checked={tab === "form"}
                                    onChange={() => setTab("form")}
                                />
                                Add Habit
                            </label>
                            <label className={`tab ${tab === "templates" ? "selected" : ""}`}>
                                <input
                                    type="radio"
                                    name="tab"
                                    value="templates"
                                    checked={tab === "templates"}
                                    onChange={() => setTab("templates")}
                                />
                                Templates
                            </label>
                        </div>
                    )}

                    <div className="tab-content">
                        {tab === "form" && <AddHabitForm setActive={setActive} onAddHabit={onAddHabit}/>}
                        {tab === "templates" && <Templates onSelectTemplate={handleTemplateSelect}/>}
                        {tab === "template-smoking" && <Smoking onBack={handleBack} onAddHabit={onAddHabit} setActive={setActive}/>}
                    </div>
                </div>
            </div>
        </>
    );
    //
}
