package org.example.backend.service;

import jakarta.transaction.Transactional;
import lombok.Data;
import org.example.backend.model.entity.User;
import org.example.backend.model.entity.habit.Habit;
import org.example.backend.model.entity.habit.badHabit.BadHabit;
import org.example.backend.model.entity.habit.goodHabit.GoodHabit;
import org.example.backend.repository.BadHabitRepository;
import org.example.backend.repository.GoodHabitRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class GoodHabitService {
    private final GoodHabitRepository goodHabitRepository;
    private final HabitService habitService;

    @Transactional
    public Habit createGoodHabit(Habit habit) {
        GoodHabit goodHabit = new GoodHabit();
        Habit h = habitService.createHabit(habit);
        goodHabit.setHabit(habitService.createHabit(habit));
        return h;
    }

    public GoodHabit getGoodHabit(Habit habit) {
        return goodHabitRepository.findGoodHabitByHabitId(habit.getId());
    }

    public List<Habit> getAllUserGoodHabits(User user) {
        List<Habit> badHabits = new ArrayList<>();
        for (Habit habit : habitService.getAllHabitsByUser(user)) {
            if (habit.isGood())
                badHabits.add(habit);
        }
        return badHabits;
    }
}
