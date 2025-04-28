package org.example.backend.controller.user;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.entity.User;
import org.example.backend.model.enums.Status;
import org.example.backend.model.response.JwtResponse;
import org.example.backend.security.DatabaseCrypto;
import org.example.backend.service.UserService;
import org.example.backend.util.JwtTokenUtils;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для обработки аутентификации и регистрации пользователей.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final DatabaseCrypto crypto;

    /**
     * Обрабатывает запрос на вход пользователя.
     *
     * @param user Объект пользователя с email и паролем
     * @return JwtResponse с токеном или сообщением об ошибке
     */
    @PostMapping("/login")
    public JwtResponse login(@RequestBody User user) {
        User userFromDB = userService.findUserByEmail(user.getEmail());

        // Проверяем существование пользователя и совпадение паролей
        if (userFromDB != null && crypto.decrypt(userFromDB.getPassword()).equals(String.valueOf(user.getPassword()))) {
            return new JwtResponse(Status.OK,
                    jwtTokenUtils.generateToken(user.getEmail(), userFromDB.getRole()),
                    userFromDB.getName());
        } else {
            return new JwtResponse(Status.ERROR, null);
        }
    }

    /**
     * Обрабатывает запрос на регистрацию нового пользователя.
     *
     * @param user Данные нового пользователя
     * @return JwtResponse с токеном или сообщением об ошибке
     */
    @PostMapping("/register")
    public JwtResponse register(@RequestBody User user) {
        // Проверка на существующего пользователя
        if (userService.findUserByEmail(user.getEmail()) != null) {
            return new JwtResponse(Status.ERROR, null);
        }

        // Установка роли (хардкод для админа - нужно вынести в конфиг)
        if (!user.getEmail().equals("viktor.nikolaenko.2005@gmail.com")) {
            user.setRole("USER");
        } else {
            user.setRole("ADMIN");
        }

        // Шифрование пароля и сохранение пользователя
        user.setPassword(crypto.encrypt(user.getPassword()));
        userService.createUser(user);

        // Генерация токена для нового пользователя
        String token = jwtTokenUtils.generateToken(user.getEmail(), user.getRole());
        return new JwtResponse(Status.OK, token, user.getName());
    }
}