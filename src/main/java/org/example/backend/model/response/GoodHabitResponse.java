package org.example.backend.model.response;

import lombok.Data;
import org.example.backend.model.entity.habit.Habit;
import org.example.backend.model.entity.habit.badHabit.Breakdown;
import org.example.backend.model.entity.habit.goodHabit.CheckIn;

import java.util.ArrayList;

@Data
public class GoodHabitResponse {
    private Habit goodHabit;
    private ArrayList<CheckIn> checkIns;


    public GoodHabitResponse(Habit goodHabit, ArrayList<CheckIn> checkIns) {
        this.goodHabit = goodHabit;
        this.checkIns = checkIns;
    }
}
