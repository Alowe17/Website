package com.example.Web_Service.repository;

import com.example.Web_Service.model.entity.RefreshToken;
import com.example.Web_Service.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);
    void deleteAllByUser(User user);
    Optional<RefreshToken> findByUser(User user);
}