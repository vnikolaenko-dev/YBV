package org.example.backend.repository;

import org.example.backend.model.entity.User;
import org.example.backend.model.entity.habit.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> getAllByUser(User user);
}
