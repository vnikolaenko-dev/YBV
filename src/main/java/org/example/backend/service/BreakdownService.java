package org.example.backend.service;

import lombok.Data;
import org.example.backend.model.entity.habit.badHabit.BadHabit;
import org.example.backend.model.entity.habit.badHabit.Breakdown;
import org.springframework.stereotype.Service;
import org.example.backend.repository.BreakdownRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Data
public class BreakdownService {
    private final BreakdownRepository breakdownRepository;

    public List<Breakdown> getAllBreakdowns(BadHabit badHabit) {
        return breakdownRepository.findAllByBadHabit(badHabit);
    }

    public void addBreakdown(Breakdown breakdown) {
        breakdownRepository.save(breakdown);
    }

    // Получение последнего срыва для указанной привычки
    public Breakdown getLatestBreakdown(BadHabit badHabit) {
        return breakdownRepository.findTopByBadHabitOrderByDateOfBreakdownDesc(badHabit)
                .orElseThrow(() -> new RuntimeException("No breakdowns found for habit: " + badHabit.getHabit().getName()));
    }

    /**
     * Возвращает количество дней с момента последнего срыва до сегодняшнего дня.
     * Если срывов нет, возвращает -1.
     */
    public long getDaysSinceLastBreakdown(BadHabit badHabit) {
        return breakdownRepository.findTopByBadHabitOrderByDateOfBreakdownDesc(badHabit)
                .map(breakdown -> {
                    LocalDateTime lastBreakdownDate = breakdown.getDateOfBreakdown();
                    LocalDateTime now = LocalDateTime.now();
                    return ChronoUnit.DAYS.between(lastBreakdownDate, now);
                })
                .orElse(-1L); // Если срывов нет
    }
}
