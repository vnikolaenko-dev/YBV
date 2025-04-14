package org.example.backend.repository;

import org.example.backend.model.entity.habit.badHabit.BadHabit;
import org.example.backend.model.entity.habit.badHabit.Breakdown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BreakdownRepository extends JpaRepository<Breakdown, Long> {
    Breakdown findByBadHabit(BadHabit badHabit);

    List<Breakdown> findAllByBadHabit(BadHabit badHabit);
}
