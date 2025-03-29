package org.example.backend.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
public class JwtTokenUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Duration jwtLifetime;

    public String generateToken(String email) {
        try {
            Date now = new Date();
            Date expiration = new Date(now.getTime() + jwtLifetime.toMillis());
            return Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(now)
                    .setExpiration(expiration)
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

}
