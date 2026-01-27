package com.example.Web_Service.repository;

import com.example.Web_Service.model.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
    Optional<Chapter> findBySlug(String slug);
}