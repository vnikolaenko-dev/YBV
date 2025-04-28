package org.example.backend.repository;

import org.example.backend.model.entity.habit.goodHabit.CheckIn;
import org.example.backend.model.entity.habit.goodHabit.GoodHabit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    List<CheckIn> findAllByGoodHabit(GoodHabit goodHabit);
}
