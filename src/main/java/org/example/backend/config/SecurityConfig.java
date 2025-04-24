package org.example.backend.config;

import lombok.RequiredArgsConstructor;
import org.example.backend.security.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Отключаем CSRF для API (если не используется)
                .csrf(AbstractHttpConfigurer::disable)

                // Включаем CORS с нашей конфигурацией
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Настройка авторизации запросов
                .authorizeHttpRequests(auth -> auth
                        // Разрешаем OPTIONS для всех путей (должно быть первым!)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Публичные эндпоинты
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()

                        // Защищенные эндпоинты
                        .requestMatchers("/user/**").authenticated()
                        .requestMatchers("/bad-habit/**").authenticated()

                        // Админские эндпоинты
                        .requestMatchers("/actuator/**").hasRole("ADMIN")

                        // Все остальные запросы требуют аутентификации
                        .anyRequest().authenticated()
                )

                // Добавляем JWT фильтр
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Разрешаем конкретные origin'ы (без шаблонов для production)
        config.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://127.0.0.1:5173",
                "https://vnikolaenko.site"
        ));

        // Альтернатива для разработки (если нужно разрешить любые поддомены/порты):
        config.setAllowedOriginPatterns(List.of("*"));

        config.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"));

        config.setAllowedHeaders(List.of(
                "Authorization", "Cache-Control", "Content-Type", "Origin",
                "Accept", "X-Requested-With", "X-XSRF-TOKEN"));

        config.setExposedHeaders(List.of(
                "Authorization", "Set-Cookie", "X-Custom-Header"));

        config.setAllowCredentials(true);
        config.setMaxAge(3600L);  // 1 час кеширования preflight

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
