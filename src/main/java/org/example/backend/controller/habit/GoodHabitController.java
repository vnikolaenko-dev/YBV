package org.example.backend.controller.habit;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.entity.User;
import org.example.backend.model.entity.habit.Habit;
import org.example.backend.model.entity.habit.badHabit.BadHabit;
import org.example.backend.model.entity.habit.badHabit.Breakdown;
import org.example.backend.model.entity.habit.goodHabit.CheckIn;
import org.example.backend.model.entity.habit.goodHabit.GoodHabit;
import org.example.backend.model.enums.Status;
import org.example.backend.model.response.BadHabitResponse;
import org.example.backend.model.response.GoodHabitResponse;
import org.example.backend.model.response.StatusResponse;
import org.example.backend.security.JwtRequestFilter;
import org.example.backend.service.*;
import org.springframework.web.bind.annotation.*;

import java.io.CharArrayReader;
import java.time.LocalDateTime;
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
    public Habit createBadHabit(@RequestHeader("Authorization") String authHeader, @RequestBody Habit habit) {
        User user = userService.getUserByEmail(jwtRequestFilter.getEmail(authHeader));
        habit.setUser(user);
        return goodHabitService.createGoodHabit(habit);
    }

    @GetMapping("/breakdown-now/{id}")
    public StatusResponse registerBreakdown(@RequestHeader("Authorization") String authHeader, @PathVariable long id) {
        User user = userService.getUserByEmail(jwtRequestFilter.getEmail(authHeader));

        Habit habit = habitService.getHabit(id, user);
        GoodHabit goodHabit = goodHabitService.getGoodHabit(habit);
        CheckIn checkIn = new CheckIn();
        checkIn.setGoodHabit(goodHabit);
        checkIn.setDateOfCheckIn(LocalDateTime.now());
        checkInService.addCheckIn(checkIn);
        return new StatusResponse(Status.OK);
    }
}
