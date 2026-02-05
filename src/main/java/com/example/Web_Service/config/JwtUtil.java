package com.example.Web_Service.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private final String secret = "qwerty12356Admin!";
    private final Algorithm algorithm = Algorithm.HMAC256(secret);

    public String generateToken (String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
                .sign(algorithm);
    }

    public String extractUsername (String token) {
        return JWT.require(algorithm)
                .build()
                .verify(token)
                .getSubject();
    }

    public boolean isTokenExpired (String token) {
        Date date = JWT.require(algorithm)
                .build()
                .verify(token)
                .getExpiresAt();

        return date.before(new Date());
    }
}