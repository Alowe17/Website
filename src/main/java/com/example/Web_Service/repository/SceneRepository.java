package com.example.Web_Service.repository;

import com.example.Web_Service.model.entity.Scene;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface SceneRepository extends JpaRepository<Scene,Integer> {
    Optional<Scene> findBySceneId(String sceneId);
}