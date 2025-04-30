package org.example.backend.repository;

import org.example.backend.model.entity.User;
import org.example.backend.model.entity.habit.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {
    Habit findHabitById(long id);

    List<Habit> findAllByUser(User user);
}
