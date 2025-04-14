package org.example.backend.model.entity.habit;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.example.backend.model.entity.User;

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

    public Habit() {

    }
}
