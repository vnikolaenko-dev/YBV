package org.example.backend.service;

import lombok.Data;
import org.example.backend.model.entity.habit.badHabit.BadHabit;
import org.example.backend.model.entity.habit.badHabit.Breakdown;
import org.example.backend.model.entity.habit.goodHabit.CheckIn;
import org.example.backend.model.entity.habit.goodHabit.GoodHabit;
import org.example.backend.repository.BreakdownRepository;
import org.example.backend.repository.CheckInRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class CheckInService {
    private final CheckInRepository checkInRepository;

    public List<CheckIn> getAllCheckIn(GoodHabit goodHabit) {
        return checkInRepository.findAllByGoodHabit(goodHabit);
    }

    public void addCheckIn(CheckIn checkIn) {
        checkInRepository.save(checkIn);
    }
}
