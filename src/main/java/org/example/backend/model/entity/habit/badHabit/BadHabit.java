package org.example.backend.model.entity.habit.badHabit;

import jakarta.persistence.*;
import lombok.Data;
import org.example.backend.model.entity.habit.Habit;

@Entity
@Table(name = "bad_habit")
@Data
public class BadHabit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id", nullable = false)
    private Habit habit;

    public BadHabit() {

    }
}
