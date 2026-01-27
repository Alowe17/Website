package com.example.Web_Service.repository;

import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.entity.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProgressRepository extends JpaRepository<UserProgress,Long> {
    Optional<UserProgress> findByUser(User user);
}