package org.example.backend.controller;


import lombok.RequiredArgsConstructor;
import org.example.backend.model.entity.User;
import org.example.backend.model.response.JwtResponse;
import org.example.backend.service.UserService;
import org.example.backend.util.JwtTokenUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody User user) {
        User userFromDB = userService.findUserByEmail(user.getEmail());
        if (userFromDB != null && userFromDB.getPassword().equals(String.valueOf(user.getPassword().hashCode()))) {
            return new JwtResponse("OK", jwtTokenUtils.generateToken(user.getEmail()));
        } else {
            return new JwtResponse("Wrong email or password", null);
        }
    }

    @PostMapping("/register")
    public JwtResponse register(@RequestBody User user) {
        if (userService.findUserByEmail(user.getEmail()) != null) {
            return new JwtResponse("User with this email is already exists", null);
        }
        userService.createUser(user);
        String token = jwtTokenUtils.generateToken(user.getEmail());
        System.out.println(token);
        return new JwtResponse("OK", token);
    }
}
