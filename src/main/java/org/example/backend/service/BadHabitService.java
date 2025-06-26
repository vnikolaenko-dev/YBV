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
    public Habit createBadHabit(Habit habit) {
        // Устанавливаем флаг, что это плохая привычка
        habit.setGood(false);

        // Сначала сохраняем Habit
        Habit savedHabit = habitService.createHabit(habit);

        // Создаем и связываем BadHabit
        BadHabit badHabit = new BadHabit();
        badHabit.setHabit(savedHabit);

        // Сохраняем BadHabit
        badHabitRepository.save(badHabit);

        // Обновляем связь в Habit
        savedHabit.setBadHabit(badHabit);

        return savedHabit;
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
