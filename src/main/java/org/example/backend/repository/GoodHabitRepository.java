package org.example.backend.repository;



import org.example.backend.model.entity.habit.goodHabit.GoodHabit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodHabitRepository extends JpaRepository<GoodHabit, Long> {
    GoodHabit findGoodHabitByHabitId(long habitId);
}
