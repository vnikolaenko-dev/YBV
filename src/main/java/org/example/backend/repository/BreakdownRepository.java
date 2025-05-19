package org.example.backend.repository;

import org.example.backend.model.entity.habit.badHabit.BadHabit;
import org.example.backend.model.entity.habit.badHabit.Breakdown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BreakdownRepository extends JpaRepository<Breakdown, Long> {
    List<Breakdown> findAllByBadHabit(BadHabit badHabit);
    Optional<Breakdown> findTopByBadHabitOrderByDateOfBreakdownDesc(BadHabit badHabit);
}
