package org.example.backend.model.entity.habit.goodHabit;

import jakarta.persistence.*;
import lombok.Data;
import org.example.backend.model.entity.habit.Habit;

@Entity
@Table(name = "good_habit")
@Data
public class GoodHabit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id", nullable = false)
    private Habit habit;

    public GoodHabit() {

    }
}
