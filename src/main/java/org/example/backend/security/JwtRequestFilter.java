package org.example.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Фильтр для аутентификации на основе JWT-токенов.
 * Проверяет наличие валидного JWT в заголовке Authorization,
 * извлекает данные пользователя и устанавливает аутентификацию в SecurityContext.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    // Секретный ключ для верификации подписи JWT (берется из application.properties)
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Основной метод фильтра, вызываемый для каждого HTTP-запроса
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Получаем заголовок Authorization из запроса
        String authHeader = request.getHeader("Authorization");

        // Проверяем, есть ли токен и начинается ли он с "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Извлекаем сам токен (удаляем "Bearer ")
            String token = authHeader.substring(7);

            try {
                // Парсим и проверяем токен с помощью секретного ключа
                Claims claims = Jwts.parser()
                        .setSigningKey(secret)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

                // Извлекаем email пользователя (subject в JWT)
                String email = claims.getSubject();
                // Извлекаем роль пользователя (кастомное поле в JWT)
                String role = claims.get("role", String.class);

                // Если email и роль присутствуют, создаем аутентифицированного пользователя
                if (email != null && role != null) {
                    // Создаем UserDetails (стандартный объект Spring Security)
                    UserDetails userDetails = User.withUsername(email)
                            .password("") // Пароль не нужен, так как проверка уже была при входе
                            .roles(role) // Устанавливаем роль из токена
                            .build();

                    // Создаем объект аутентификации
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null, // credentials не нужны
                                    userDetails.getAuthorities() // права/роли пользователя
                            );

                    // Добавляем детали запроса (например, IP-адрес)
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Устанавливаем аутентификацию в SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            } catch (Exception e) {
                // Если токен невалидный (истек, подделан и т.д.), возвращаем 401 Unauthorized
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                return;
            }
        }

        // Передаем запрос дальше по цепочке фильтров
        chain.doFilter(request, response);
    }

    /**
     * Вспомогательный метод для извлечения email из JWT-токена
     *
     * @param authorizationHeader - заголовок Authorization ("Bearer <token>")
     * @return email пользователя или null, если токен невалидный
     */
    public String getEmail(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            try {
                // Парсим токен и извлекаем subject (email)
                return Jwts.parser()
                        .setSigningKey(secret)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody()
                        .getSubject();
            } catch (Exception e) {
                // Логируем ошибку, но не прерываем выполнение
                logger.warn("JWT Token is invalid: " + e.getMessage());
            }
        }
        return null;
    }
}