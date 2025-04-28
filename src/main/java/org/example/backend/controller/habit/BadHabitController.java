package org.example.backend.controller.habit;

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

    @GetMapping("/get/{id}")
    public BadHabitResponse getBadHabit(@RequestHeader("Authorization") String authHeader, @PathVariable long id) {
        User user = userService.getUserByEmail(jwtRequestFilter.getEmail(authHeader));
        Habit habit = habitService.getHabit(id, user);

        BadHabit badHabit = badHabitService.getBadHabit(habit);
        ArrayList<Breakdown> breakdowns = (ArrayList<Breakdown>) breakdownService.getAllBreakdowns(badHabit);

        return new BadHabitResponse(habit, breakdowns);
    }


    @PostMapping("/create")
    public Habit createBadHabit(@RequestHeader("Authorization") String authHeader, @RequestBody Habit habit) {
        User user = userService.getUserByEmail(jwtRequestFilter.getEmail(authHeader));
        habit.setUser(user);
        return badHabitService.createBadHabit(habit);
    }

    @GetMapping("/breakdown-now/{id}")
    public StatusResponse registerBreakdown(@RequestHeader("Authorization") String authHeader, @PathVariable long id) {
        User user = userService.getUserByEmail(jwtRequestFilter.getEmail(authHeader));

        Habit habit = habitService.getHabit(id, user);
        BadHabit badHabit = badHabitService.getBadHabit(habit);
        Breakdown breakdown = new Breakdown();
        breakdown.setBadHabit(badHabit);
        breakdown.setDateOfBreakdown(LocalDateTime.now());
        breakdownService.addBreakdown(breakdown);
        return new StatusResponse(Status.OK);
    }


}
