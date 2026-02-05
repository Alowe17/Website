package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.*;
import com.example.Web_Service.model.entity.Chapter;
import com.example.Web_Service.repository.ChapterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterService {
    private final ChapterRepository chapterRepository;

    public ChapterService (ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    public ChapterDto getChapter (String slug) {
        Chapter chapter = chapterRepository.findBySlug(slug).orElseThrow(() -> new RuntimeException("Chapter Not Found!"));
        return new ChapterDto(chapter.getTitle(), chapter.getDescription(), chapter.getNumber(), chapter.getSlug(), chapter.getImage(), chapter.getSceneId());
    }
}