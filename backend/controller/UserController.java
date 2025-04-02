package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.entity.User;
import org.example.backend.model.enums.Status;
import org.example.backend.security.JwtRequestFilter;
import org.example.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtRequestFilter jwtRequestFilter;

    @GetMapping
    @RequestMapping("/change-name/{name}")
    public Status changeName(@RequestHeader("Authorization") String authHeader, @PathVariable String name) {
        try {
            String email = jwtRequestFilter.getEmail(authHeader);
            User user = userService.findUserByEmail(email);
            user.setName(name);
            userService.updateUser(user);
            return Status.OK;
        } catch (Exception e) {
            return Status.ERROR;
        }
    }

    @GetMapping
    @RequestMapping("/change-email/{new_email}")
    public Status changeEmail(@RequestHeader("Authorization") String authHeader, @PathVariable String new_email) {
        try {
            String email = jwtRequestFilter.getEmail(authHeader);
            User user = userService.findUserByEmail(email);
            user.setEmail(new_email);
            userService.updateUser(user);
            return Status.OK;
        } catch (Exception e) {
            return Status.ERROR;
        }
    }

    @GetMapping
    @RequestMapping("/change-password/{password}")
    public Status changePassword(@RequestHeader("Authorization") String authHeader, @PathVariable String password) {
        try {
            String email = jwtRequestFilter.getEmail(authHeader);
            User user = userService.findUserByEmail(email);
            user.setPassword(password);
            userService.updateUser(user);
            return Status.OK;
        } catch (Exception e) {
            return Status.ERROR;
        }
    }
}
