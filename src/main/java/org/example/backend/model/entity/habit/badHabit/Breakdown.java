package org.example.backend.model.entity.habit.badHabit;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "breakdown")
@Data
public class Breakdown {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @Column(nullable = false)
    private LocalDateTime dateOfBreakdown;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bad_habit_id", nullable = false)
    @JsonIgnore
    private BadHabit badHabit;

    public Breakdown() {

    }
}
