package com.example.Web_Service.service;

import com.example.Web_Service.model.entity.RefreshToken;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.repository.RefreshTokenRepository;
import com.example.Web_Service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenService (RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public RefreshToken create (String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found!"));

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        refreshToken.setCreatedAt(LocalDateTime.now());
        refreshToken.setExpiredAt(LocalDateTime.now().plusDays(30));
        refreshToken.setRevoked(false);
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken validate (String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Invalid refresh token!"));

        if (refreshToken.isRevoked()) {
            throw new RuntimeException("Refresh token is revoked!");
        }

        if (refreshToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token is expired!");
        }

        return refreshToken;
    }

    public void revoke (RefreshToken refreshToken) {
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }

    public void revokeAll (User user) {
        refreshTokenRepository.deleteAllByUser(user);
    }
}