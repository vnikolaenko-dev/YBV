package org.example.backend.repository;


import org.example.backend.model.entity.habit.badHabit.BadHabit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BadHabitRepository extends JpaRepository<BadHabit, Long> {

}
