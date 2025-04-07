package org.example.backend.controller;


import lombok.RequiredArgsConstructor;
import org.example.backend.model.entity.User;
import org.example.backend.model.enums.Status;
import org.example.backend.model.response.JwtResponse;
import org.example.backend.security.DatabaseCrypto;
import org.example.backend.service.UserService;
import org.example.backend.util.JwtTokenUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final DatabaseCrypto crypto;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody User user) {
        User userFromDB = userService.findUserByEmail(user.getEmail());

        if (userFromDB != null && crypto.decrypt(userFromDB.getPassword()).equals(String.valueOf(user.getPassword()))) {
            return new JwtResponse(Status.OK, jwtTokenUtils.generateToken(user.getEmail(), userFromDB.getRole()), userFromDB.getName());
        } else {

            return new JwtResponse(Status.ERROR, null);
        }
    }

    @PostMapping("/register")
    public JwtResponse register(@RequestBody User user) {
        if (userService.findUserByEmail(user.getEmail()) != null) {
            return new JwtResponse(Status.ERROR, null);
        }

        if (!user.getEmail().equals("viktor.nikolaenko.2005@gmail.com")) {
            user.setRole("USER");
        } else {
            user.setRole("ADMIN");
        }

        user.setPassword(crypto.encrypt(user.getPassword()));
        userService.createUser(user);
        String token = jwtTokenUtils.generateToken(user.getEmail(), user.getRole());
        return new JwtResponse(Status.OK, token, user.getName());
    }
}
