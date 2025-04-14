package org.example.backend.repository;

import org.example.backend.model.entity.habit.badHabit.BadHabit;
import org.example.backend.model.entity.habit.badHabit.Breakdown;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreakdownRepository extends JpaRepository<Breakdown, Long> {
    Breakdown findByBadHabit(BadHabit badHabit);
}
