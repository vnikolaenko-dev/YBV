package org.example.backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

/**
 * Утилитарный класс для работы с JWT токенами.
 * Обеспечивает генерацию, парсинг и валидацию JWT токенов.
 */
@Component
public class JwtTokenUtils {

    // Секретный ключ для подписи токенов, берется из application.properties
    @Value("${jwt.secret}")
    private String secret;

    // Время жизни токена, берется из application.properties
    @Value("${jwt.expiration}")
    private Duration jwtLifetime;

    /**
     * Генерирует JWT токен на основе email пользователя и его роли.
     *
     * @param email Email пользователя (будет subject в токене)
     * @param role Роль пользователя (добавляется в claims)
     * @return Сгенерированный JWT токен или null в случае ошибки
     */
    public String generateToken(String email, String role) {
        try {
            Date now = new Date();
            // Вычисляем дату истечения токена
            Date expiration = new Date(now.getTime() + jwtLifetime.toMillis());

            // Строим JWT токен:
            return Jwts.builder()
                    .setSubject(email)          // Устанавливаем subject (обычно email/userId)
                    .setIssuedAt(now)           // Время создания токена
                    .setExpiration(expiration)  // Время истечения токена
                    .addClaims(Map.of("role", role)) // Добавляем кастомные claims (в данном случае роль)
                    .signWith(SignatureAlgorithm.HS256, secret) // Алгоритм подписи и секретный ключ
                    .compact();                 // Генерируем окончательную строку токена
        } catch (Exception e) {
            // В реальном приложении лучше использовать логгер вместо System.out
            System.out.println("Ошибка генерации токена: " + e.getMessage());
            return null;
        }
    }

    /**
     * Извлекает email пользователя из JWT токена.
     *
     * @param token JWT токен
     * @return Email пользователя (subject токена)
     */
    private String getEmailFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)         // Устанавливаем ключ для верификации подписи
                .build()
                .parseClaimsJws(token)        // Парсим токен и проверяем подпись
                .getBody()                    // Получаем тело токена (claims)
                .getSubject();                // Извлекаем subject (email)
    }

    /**
     * Извлекает роль пользователя из JWT токена.
     *
     * @param token JWT токен
     * @return Роль пользователя
     */
    public String getUserRoleFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)        // Ключ для верификации
                .build()
                .parseSignedClaims(token)     // Парсим токен с проверкой подписи
                .getPayload();                // Получаем payload (claims)

        return claims.get("role", String.class); // Извлекаем значение claim с именем "role"
    }
}