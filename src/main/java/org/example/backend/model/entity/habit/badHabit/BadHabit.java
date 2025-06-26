package org.example.backend.model.entity.habit.badHabit;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JoinColumn(name = "habit_id", unique = true, nullable = false)
    @JsonIgnore
    private Habit habit;

    public BadHabit() {

    }

    @Override
    public String toString() {
        return "BadHabit{" +
                "id=" + id +
                ", habit=" + habit +
                '}';
    }
}
