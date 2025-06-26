package org.example.backend.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.example.backend.model.entity.User;
import org.example.backend.model.entity.habit.Habit;
import org.example.backend.repository.HabitRepository;
import org.example.backend.security.DatabaseCrypto;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Data
public class HabitService {
    private final DatabaseCrypto crypto;
    private final HabitRepository habitRepository;

    @Transactional
    public Habit createHabit(Habit habit) {
        return habitRepository.save(habit);
    }

    @Transactional
    public Habit getHabit(long id, User user) {
        if (user == null || user.getEmail() == null) {
            throw new IllegalArgumentException("User or user email is null");
        }

        Habit habit = habitRepository.findByIdWithUser(id)
                .orElseThrow(() -> new EntityNotFoundException("Habit not found with id: " + id));

        if (crypto.decrypt(habit.getUser().getPassword()).equals(String.valueOf(user.getPassword()))) {
            throw new AccessDeniedException("User does not have access to this habit");
        }

        return habit;
    }

    public List<Habit> getAllHabitsByUser(User user) {
        return habitRepository.findAllByUser(user);
    }
}
