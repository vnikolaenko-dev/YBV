package org.example.backend.service;

import lombok.Data;
import org.example.backend.model.entity.habit.badHabit.BadHabit;
import org.example.backend.model.entity.habit.badHabit.Breakdown;
import org.springframework.stereotype.Service;
import org.example.backend.repository.BreakdownRepository;

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
}
