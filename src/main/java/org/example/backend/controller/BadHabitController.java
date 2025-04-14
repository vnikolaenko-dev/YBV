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
import org.example.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/bad-habit")
@RequiredArgsConstructor
public class BadHabitController {
    private final UserService userService;
    private final JwtRequestFilter jwtRequestFilter;
    private final BadHabitService badHabitService;
    private final BreakdownService breakdownService;

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

    @PostMapping("/get/{id}")
    public StatusResponse getBadHabit(@RequestHeader("Authorization") String authHeader, @PathVariable int id) {
        try {
//            String email = jwtRequestFilter.getEmail(authHeader);
//            User user = userService.findUserByEmail(email);
//            habit.setUser(user);
//            badHabitService.createBadHabit(habit);
            return new StatusResponse(Status.OK);
        } catch (Exception e) {
            return new StatusResponse(Status.ERROR);
        }
    }

    @GetMapping("/get-all")
    public StatusResponse getAll(@RequestHeader("Authorization") String authHeader) {
        try {
            String email = jwtRequestFilter.getEmail(authHeader);
            User user = userService.findUserByEmail(email);
            ArrayList<Habit> badHabits = (ArrayList<Habit>) badHabitService.getAllUserBadHabits(user);
            ArrayList<BadHabitResponse> badHabitResponses = new ArrayList<>();

            for (Habit badHabit : badHabits) {
//                Breakdown breakdown =
//                badHabitResponses.add(new BadHabitResponse(badHabit, breakdownService.));
            }

            return new StatusResponse(Status.OK);
        } catch (Exception e) {
            return new StatusResponse(Status.ERROR);
        }
    }

    @GetMapping("/breakdown/{id}")
    public StatusResponse registerBreakdown(@RequestHeader("Authorization") String authHeader, @PathVariable String id) {
        try {
//            String email = jwtRequestFilter.getEmail(authHeader);
//            User user = userService.findUserByEmail(email);
//            habit.setUser(user);
//            badHabitService.createBadHabit(habit);
            return new StatusResponse(Status.OK);
        } catch (Exception e) {
            return new StatusResponse(Status.ERROR);
        }
    }

    @GetMapping("/remove/{id}")
    public StatusResponse removeBadHabit(@RequestHeader("Authorization") String authHeader, @PathVariable String id) {
        try {
//            String email = jwtRequestFilter.getEmail(authHeader);
//            User user = userService.findUserByEmail(email);
//            habit.setUser(user);
//            badHabitService.createBadHabit(habit);
            return new StatusResponse(Status.OK);
        } catch (Exception e) {
            return new StatusResponse(Status.ERROR);
        }
    }

}
