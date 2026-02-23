package com.example.Web_Service.repository;

import com.example.Web_Service.model.entity.GameCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameCharacterRepository extends JpaRepository<GameCharacter, Integer> {
    Optional<GameCharacter> findById(Long id);
}