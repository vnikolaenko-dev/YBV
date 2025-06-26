package org.example.backend.model.entity.habit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.example.backend.model.entity.User;
import org.example.backend.model.entity.habit.badHabit.BadHabit;
import org.example.backend.model.entity.habit.goodHabit.GoodHabit;

import java.time.LocalDateTime;

@Entity
@Table(name = "habit")
@Data
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime dateOfStart;

    @Column(nullable = false)
    private int target;

    @Column(nullable = false)
    private boolean isGood;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Transient  // Поле не сохраняется в БД
    private BadHabit badHabit;

    @Transient  // Поле не сохраняется в БД
    private GoodHabit goodHabit;
    public Habit() {

    }

    @Override
    public String toString() {
        return "Habit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfStart=" + dateOfStart +
                ", target=" + target +
                ", isGood=" + isGood +
                ", user=" + user +
                '}';
    }
}
