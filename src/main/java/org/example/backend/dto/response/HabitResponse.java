package org.example.backend.dto.response;

import lombok.Data;
import org.example.backend.model.entity.habit.Habit;

@Data
public class HabitResponse {
    private Habit habit;
    private long maxScore;
    private long currentScore;
}
