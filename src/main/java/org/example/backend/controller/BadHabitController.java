package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.entity.User;
import org.example.backend.model.entity.habit.Habit;
import org.example.backend.model.entity.habit.badHabit.BadHabit;
import org.example.backend.model.entity.habit.badHabit.Breakdown;
import org.example.backend.model.enums.Status;
import org.example.backend.model.response.BadHabitResponse;
import org.example.backend.model.response.StatusResponse;
import org.example.backend.security.JwtRequestFilter;
import org.example.backend.service.BadHabitService;
import org.example.backend.service.BreakdownService;
import org.example.backend.service.HabitService;
import org.example.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
@RequestMapping("/bad-habit")
@RequiredArgsConstructor
public class BadHabitController {
    private final UserService userService;
    private final JwtRequestFilter jwtRequestFilter;
    private final BadHabitService badHabitService;
    private final BreakdownService breakdownService;
    private final HabitService habitService;

    // TODO пользователь является владельцем habit
    @PostMapping("/get/{id}")
    public BadHabitResponse getBadHabit(@RequestHeader("Authorization") String authHeader, @PathVariable long id) {
        try {
            String email = jwtRequestFilter.getEmail(authHeader);
            User user = userService.findUserByEmail(email);
            Habit habit = habitService.getHabit(id);

            BadHabit badHabit = badHabitService.getBadHabit(habit);
            ArrayList<Breakdown> breakdowns = (ArrayList<Breakdown>) breakdownService.getAllBreakdowns(badHabit);

            return new BadHabitResponse(habit, breakdowns);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/get-all")
    public ArrayList<BadHabitResponse> getAll(@RequestHeader("Authorization") String authHeader) {
        try {
            String email = jwtRequestFilter.getEmail(authHeader);
            User user = userService.findUserByEmail(email);
            ArrayList<Habit> badHabits = (ArrayList<Habit>) badHabitService.getAllUserBadHabits(user);
            ArrayList<BadHabitResponse> badHabitResponses = new ArrayList<>();

            for (Habit habit : badHabits) {
                BadHabit badHabit = badHabitService.getBadHabit(habit);
                ArrayList<Breakdown> breakdowns = (ArrayList<Breakdown>) breakdownService.getAllBreakdowns(badHabit);
                badHabitResponses.add(new BadHabitResponse(habit, breakdowns));
            }

            return badHabitResponses;
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/create")
    public StatusResponse createBadHabit(@RequestHeader("Authorization") String authHeader, @RequestBody Habit habit) {
        try {
            String email = jwtRequestFilter.getEmail(authHeader);
            User user = userService.findUserByEmail(email);
            habit.setUser(user);
            badHabitService.createBadHabit(habit);
            return new StatusResponse(Status.OK);
        } catch (Exception e) {
            return new StatusResponse(Status.ERROR);
        }
    }

    // TODO пользователь является владельцем habit
    @GetMapping("/breakdown-now/{id}")
    public StatusResponse registerBreakdown(@RequestHeader("Authorization") String authHeader, @PathVariable long id) {
        try {
            String email = jwtRequestFilter.getEmail(authHeader);
            User user = userService.findUserByEmail(email);

            Habit habit = habitService.getHabit(id);
            BadHabit badHabit = badHabitService.getBadHabit(habit);
            Breakdown breakdown = new Breakdown();
            breakdown.setBadHabit(badHabit);
            breakdown.setDateOfBreakdown(LocalDateTime.now());
            breakdownService.addBreakdown(breakdown);
            return new StatusResponse(Status.OK);
        } catch (Exception e) {
            return new StatusResponse(Status.ERROR);
        }
    }


}
