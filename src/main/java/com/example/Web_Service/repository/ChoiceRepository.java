package com.example.Web_Service.repository;

import com.example.Web_Service.model.entity.Choice;
import com.example.Web_Service.model.entity.Scene;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChoiceRepository extends JpaRepository<Choice, Integer> {
    List<Choice> findBySceneFrom(Scene scene);
}