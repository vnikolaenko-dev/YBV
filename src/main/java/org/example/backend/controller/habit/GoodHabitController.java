package org.example.backend.controller.habit;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.request.HabitRequest;
import org.example.backend.dto.response.ScoreResponse;
import org.example.backend.model.entity.User;
import org.example.backend.model.entity.habit.Habit;
import org.example.backend.model.entity.habit.goodHabit.CheckIn;
import org.example.backend.model.entity.habit.goodHabit.GoodHabit;
import org.example.backend.model.enums.Status;
import org.example.backend.dto.response.GoodHabitResponse;
import org.example.backend.dto.response.StatusResponse;
import org.example.backend.security.JwtRequestFilter;
import org.example.backend.service.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@RestController
@RequestMapping("/good-habit")
@RequiredArgsConstructor
public class GoodHabitController {
    private final UserService userService;
    private final JwtRequestFilter jwtRequestFilter;
    private final GoodHabitService goodHabitService;
    private final CheckInService checkInService;
    private final HabitService habitService;

    @GetMapping("/get/{id}")
    public GoodHabitResponse getBadHabit(@RequestHeader("Authorization") String authHeader, @PathVariable long id) {
        User user = userService.getUserByEmail(jwtRequestFilter.getEmail(authHeader));
        Habit habit = habitService.getHabit(id, user);

        GoodHabit goodHabit = goodHabitService.getGoodHabit(habit);
        ArrayList<CheckIn> checkIns = (ArrayList<CheckIn>) checkInService.getAllCheckIn(goodHabit);

        return new GoodHabitResponse(habit, checkIns);
    }


    @PostMapping("/create")
    public Habit createBadHabit(@RequestHeader("Authorization") String authHeader, @RequestBody HabitRequest habit) {
        User user = userService.getUserByEmail(jwtRequestFilter.getEmail(authHeader));

        Habit newHabit = new Habit();
        newHabit.setName(habit.getName());
        newHabit.setGood(habit.isGood());
        newHabit.setTarget(30);
        newHabit.setDateOfStart(habit.getDateOfStart());

        newHabit.setUser(user);
        return goodHabitService.createGoodHabit(newHabit);
    }

    @GetMapping("/check-in/{id}/{time}")
    public ScoreResponse registerCheckIn(@RequestHeader("Authorization") String authHeader, @PathVariable long id, @PathVariable String time) {
        time += "T00:00:00";
        User user = userService.getUserByEmail(jwtRequestFilter.getEmail(authHeader));

        Habit habit = habitService.getHabit(id, user);
        GoodHabit goodHabit = goodHabitService.getGoodHabit(habit);

        CheckIn checkIn = new CheckIn();
        checkIn.setGoodHabit(goodHabit);
        checkIn.setDateOfCheckIn(LocalDateTime.parse(time));
        checkInService.addCheckIn(checkIn);

        ScoreResponse response = new ScoreResponse();


        long days = checkInService.getDaysSinceLastCheckIn(goodHabit);
        if (days >= 0) {
            response.setCurrentScore(days);
            response.setMaxScore(days);
        } else {
            LocalDateTime now = LocalDateTime.now();
            response.setCurrentScore(ChronoUnit.DAYS.between(habit.getDateOfStart(), now));
            response.setMaxScore(ChronoUnit.DAYS.between(habit.getDateOfStart(), now));
        }

        return response;
    }
}
