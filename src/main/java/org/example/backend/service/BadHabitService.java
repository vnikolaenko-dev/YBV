package org.example.backend.service;

import jakarta.transaction.Transactional;
import lombok.Data;
import org.example.backend.model.entity.User;
import org.example.backend.model.entity.habit.Habit;
import org.example.backend.model.entity.habit.badHabit.BadHabit;
import org.example.backend.repository.BadHabitRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class BadHabitService {
    private final BadHabitRepository badHabitRepository;
    private final HabitService habitService;

    @Transactional
    public void createBadHabit(Habit habit) {
        BadHabit badHabit = new BadHabit();
        badHabit.setHabit(habitService.createHabit(habit));
        badHabitRepository.save(badHabit);
    }

    public BadHabit getBadHabit(Habit habit) {
        return badHabitRepository.findBadHabitByHabitId(habit.getId());
    }

    public List<Habit> getAllUserBadHabits(User user) {
        List<Habit> badHabits = new ArrayList<>();
        for (Habit habit : habitService.getAllHabitsByUser(user)) {
            if (!habit.isGood())
                badHabits.add(habit);
        }
        return badHabits;
    }
}
