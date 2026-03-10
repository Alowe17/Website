package com.example.Web_Service.repository;

import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.entity.UserGameState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserGameStateRepository extends JpaRepository<UserGameState, Integer> {
    Optional<UserGameState> findByUser(User user);
}