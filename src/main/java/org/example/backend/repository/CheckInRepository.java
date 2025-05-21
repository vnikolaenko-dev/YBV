package org.example.backend.repository;

import aj.org.objectweb.asm.commons.Remapper;
import org.example.backend.model.entity.habit.badHabit.Breakdown;
import org.example.backend.model.entity.habit.goodHabit.CheckIn;
import org.example.backend.model.entity.habit.goodHabit.GoodHabit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    List<CheckIn> findAllByGoodHabit(GoodHabit goodHabit);

    Optional<CheckIn> findTopByGoodHabitOrderByDateOfCheckInDesc(GoodHabit goodHabit);
}
