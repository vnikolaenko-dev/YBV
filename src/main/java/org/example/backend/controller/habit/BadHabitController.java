package org.example.backend.controller.habit;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.request.HabitRequest;
import org.example.backend.dto.response.ScoreResponse;
import org.example.backend.model.entity.User;
import org.example.backend.model.entity.habit.Habit;
import org.example.backend.model.entity.habit.badHabit.BadHabit;
import org.example.backend.model.entity.habit.badHabit.Breakdown;
import org.example.backend.dto.response.BadHabitResponse;
import org.example.backend.security.JwtRequestFilter;
import org.example.backend.service.BadHabitService;
import org.example.backend.service.BreakdownService;
import org.example.backend.service.HabitService;
import org.example.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    public Habit createBadHabit(@RequestHeader("Authorization") String authHeader, @RequestBody HabitRequest habit) {
        User user = userService.getUserByEmail(jwtRequestFilter.getEmail(authHeader));

        Habit newHabit = new Habit();
        newHabit.setName(habit.getName());
        newHabit.setGood(habit.isGood());
        newHabit.setTarget(30);
        newHabit.setDateOfStart(habit.getDateOfStart());

        newHabit.setUser(user);
        return badHabitService.createBadHabit(newHabit);
    }

    @GetMapping("/breakdown-now/{id}/{time}")
    public ScoreResponse registerBreakdown(@RequestHeader("Authorization") String authHeader, @PathVariable long id, @PathVariable String time) {
        User user = userService.getUserByEmail(jwtRequestFilter.getEmail(authHeader));

        Habit habit = habitService.getHabit(id, user);
        BadHabit badHabit = badHabitService.getBadHabit(habit);

        Breakdown breakdown = new Breakdown();
        breakdown.setBadHabit(badHabit);
        breakdown.setDateOfBreakdown(LocalDateTime.parse(time));
        breakdownService.addBreakdown(breakdown);

        ScoreResponse response = new ScoreResponse();


        long days = breakdownService.getDaysSinceLastBreakdown(badHabit);
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
