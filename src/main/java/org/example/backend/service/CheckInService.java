package org.example.backend.service;

import lombok.Data;
import org.example.backend.model.entity.habit.goodHabit.CheckIn;
import org.example.backend.model.entity.habit.goodHabit.GoodHabit;
import org.example.backend.repository.CheckInRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    public long getDaysSinceLastCheckIn(GoodHabit goodHabit) {
        return checkInRepository.findTopByGoodHabitOrderByDateOfCheckInDesc(goodHabit)
                .map(checkIn -> {
                    LocalDateTime lastCheckInDate = checkIn.getDateOfCheckIn();
                    LocalDateTime now = LocalDateTime.now();
                    return ChronoUnit.DAYS.between(lastCheckInDate, now);
                })
                .orElse(-1L); // Если срывов нет
    }
}
