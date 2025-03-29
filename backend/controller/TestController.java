package org.example.backend.controller;

import org.example.backend.model.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/hello/{name}")
    public String testGet(@PathVariable String name){
        return "Hello, " + name;
    }

    @PostMapping("/create")
    public String createUser(@RequestBody User user){
        return "Hello, " + user.getName() + ", " + user.getPassword() + ", " + user.getEmail();
    }

    @GetMapping("/header")
    public String testHeader(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return "Token received: " + token;
        } else {
            return "Authorization header is missing or invalid.";
        }
    }
}
