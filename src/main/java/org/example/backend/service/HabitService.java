package org.example.backend.service;

import jakarta.transaction.Transactional;
import lombok.Data;
import org.example.backend.model.entity.User;
import org.example.backend.model.entity.habit.Habit;
import org.example.backend.model.entity.habit.badHabit.BadHabit;
import org.example.backend.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Data
public class HabitService {
    private final HabitRepository habitRepository;

    @Transactional
    public Habit createHabit(Habit habit) {
        return habitRepository.save(habit);
    }

    public Habit getHabit(long id, User user) {
        Habit habit = habitRepository.findHabitById(id);
        if (habit == null || !Objects.equals(habit.getUser().getEmail(), user.getEmail())) {
            return null;
        }
        return habitRepository.findHabitById(id);
    }

    public List<Habit> getAllHabitsByUser(User user) {
        return habitRepository.getAllByUser(user);
    }
}
