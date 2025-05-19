package org.example.backend.controller.habit;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.response.BadHabitResponse;
import org.example.backend.dto.response.HabitResponse;
import org.example.backend.model.entity.User;
import org.example.backend.model.entity.habit.Habit;
import org.example.backend.model.entity.habit.badHabit.BadHabit;
import org.example.backend.model.entity.habit.badHabit.Breakdown;
import org.example.backend.model.entity.habit.goodHabit.GoodHabit;
import org.example.backend.security.JwtRequestFilter;
import org.example.backend.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@RestController
@RequestMapping("/habit")
@RequiredArgsConstructor
public class HabitController {
    private final UserService userService;
    private final JwtRequestFilter jwtRequestFilter;
    private final HabitService habitService;
    private final BadHabitService badHabitService;
    private final BreakdownService breakdownService;
    private final GoodHabitService goodHabitService;
    private final CheckInService checkInService;

    @GetMapping("/get-all")
    public ArrayList<HabitResponse> getAll(@RequestHeader("Authorization") String authHeader) {

        User user = userService.getUserByEmail(jwtRequestFilter.getEmail(authHeader));

        try {
            ArrayList<HabitResponse> responses = new ArrayList<>();
            for (Habit habit : habitService.getAllHabitsByUser(user)) {
                HabitResponse habitResponse = new HabitResponse();
                habitResponse.setHabit(habit);
                try {
                    if (!habit.isGood()) {
                        BadHabit badHabit = badHabitService.getBadHabit(habit);
                        long days = breakdownService.getDaysSinceLastBreakdown(badHabit);
                        if (days >= 0) {
                            habitResponse.setCurrentScore(days);
                            habitResponse.setMaxScore(days);
                        } else {
                            LocalDateTime now = LocalDateTime.now();
                            habitResponse.setCurrentScore(ChronoUnit.DAYS.between(habit.getDateOfStart(), now));
                            habitResponse.setMaxScore(ChronoUnit.DAYS.between(habit.getDateOfStart(), now));
                        }
                    } else {
                        GoodHabit goodHabit = goodHabitService.getGoodHabit(habit);
                        long count = checkInService.getAllCheckIn(goodHabit).size();
                        habitResponse.setCurrentScore(count);
                        habitResponse.setMaxScore(count);
                    }
                } catch (Exception ignored) {

                }
                responses.add(habitResponse);
            }
            return responses;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

    }


}
