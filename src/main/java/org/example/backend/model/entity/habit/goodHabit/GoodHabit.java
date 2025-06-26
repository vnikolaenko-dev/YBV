package org.example.backend.model.entity.habit.goodHabit;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JoinColumn(name = "habit_id", unique = true, nullable = false)
    @JsonIgnore
    private Habit habit;

    public GoodHabit() {

    }
}
