package com.example.Web_Service.repository;

import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.entity.UserGameAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserGameAttributeRepository extends JpaRepository<UserGameAttribute,Integer> {
    List<UserGameAttribute> findByUser(User user);
    Optional<UserGameAttribute> findByUserAndEffectKey(User user, String effectKey);
}