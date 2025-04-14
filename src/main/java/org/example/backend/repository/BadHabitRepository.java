package org.example.backend.repository;


import org.example.backend.model.entity.habit.badHabit.BadHabit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadHabitRepository extends JpaRepository<BadHabit, Long> {

}
