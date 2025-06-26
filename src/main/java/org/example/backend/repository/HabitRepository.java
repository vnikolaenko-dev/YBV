package org.example.backend.repository;

import org.example.backend.model.entity.User;
import org.example.backend.model.entity.habit.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {
    @Query("SELECT h FROM Habit h JOIN FETCH h.user WHERE h.id = :id")
    Optional<Habit> findByIdWithUser(@Param("id") long id);

    Habit findHabitById(long id);

    List<Habit> findAllByUser(User user);
}
