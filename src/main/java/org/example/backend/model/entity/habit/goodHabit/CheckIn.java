package org.example.backend.model.entity.habit.goodHabit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.example.backend.model.entity.habit.badHabit.BadHabit;

import java.time.LocalDateTime;

@Entity
@Table(name = "check_in")
@Data
public class CheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @Column(nullable = false)
    private LocalDateTime dateOfCheckIn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "good_habit_id", nullable = false)
    @JsonIgnore
    private GoodHabit goodHabit;

    public CheckIn() {

    }
}
