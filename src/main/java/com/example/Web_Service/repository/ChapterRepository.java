package com.example.Web_Service.repository;

import com.example.Web_Service.model.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
    Optional<Chapter> findBySlug(String slug);
    @Query("SELECT c FROM Chapter c")
    List<Chapter> allChapters();
}