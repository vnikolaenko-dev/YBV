package org.example.backend.controller.habit;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.entity.User;
import org.example.backend.model.entity.habit.Habit;
import org.example.backend.model.entity.habit.badHabit.BadHabit;
import org.example.backend.model.entity.habit.badHabit.Breakdown;
import org.example.backend.model.response.BadHabitResponse;
import org.example.backend.security.JwtRequestFilter;
import org.example.backend.service.BadHabitService;
import org.example.backend.service.BreakdownService;
import org.example.backend.service.HabitService;
import org.example.backend.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/habit")
@RequiredArgsConstructor
public class HabitController {
    private final UserService userService;
    private final JwtRequestFilter jwtRequestFilter;
    private final HabitService habitService;

    @GetMapping("/get-all")
    public ArrayList<Habit> getAll(@RequestHeader("Authorization") String authHeader) {

        User user = userService.getUserByEmail(jwtRequestFilter.getEmail(authHeader));

        try {
            return (ArrayList<Habit>) habitService.getAllHabitsByUser(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

    }


}
