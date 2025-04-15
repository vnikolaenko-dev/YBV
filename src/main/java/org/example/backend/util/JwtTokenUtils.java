package org.example.backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Duration jwtLifetime;

    public String generateToken(String email, String role) {
        try {
            Date now = new Date();
            Date expiration = new Date(now.getTime() + jwtLifetime.toMillis());
            return Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(now)
                    .setExpiration(expiration)
                    .addClaims(Map.of("role", role)) // Добавляем роль в токен
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }


    private String getEmailFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret) // Используем Key вместо String
                .build()
                .parseClaimsJws(token) // Используем parseClaimsJws вместо parseClaimsJwt
                .getBody()
                .getSubject();
    }

    public String getUserRoleFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .build().parseSignedClaims(token).getPayload();

        return claims.get("role", String.class); // Достаем роль
    }

}
