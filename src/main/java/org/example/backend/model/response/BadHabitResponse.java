package org.example.backend.model.response;

import lombok.Data;
import org.example.backend.model.entity.habit.Habit;
import org.example.backend.model.entity.habit.badHabit.Breakdown;

import java.util.ArrayList;

@Data
public class BadHabitResponse {
    private Habit badHabit;
    private ArrayList<Breakdown> lastBreakdown;


    public BadHabitResponse(Habit badHabit, ArrayList<Breakdown> lastBreakdown) {
        this.badHabit = badHabit;
        this.lastBreakdown = lastBreakdown;
    }
}
