package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.*;
import com.example.Web_Service.model.entity.Chapter;
import com.example.Web_Service.model.entity.GameCharacter;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.repository.ChapterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterService {
    private final ChapterRepository chapterRepository;

    public ChapterService (ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    public ChapterDto getChapterDto (String slug) {
        Chapter chapter = chapterRepository.findBySlug(slug).orElseThrow(() -> new RuntimeException("Chapter Not Found!"));
        return new ChapterDto(chapter.getTitle(), chapter.getDescription(), chapter.getNumber(), chapter.getSlug(), chapter.getImage(), chapter.getSceneId(), chapter.getStatus());
    }

    public Chapter getChapter (int id) {
        return chapterRepository.findById(id).orElseThrow(() -> new RuntimeException("Chapter Not Found!"));
    }

    public List<ChapterContentDto> getChapters (User user) {
        List<Chapter> chapters = chapterRepository.allChapters();

        if (chapters.isEmpty()) {
            return null;
        }

        if (user.getRole().name().equals("ADMINISTRATOR") ||
                user.getRole().name().equals("TESTER") ||
                user.getRole().name().equals("NARRATIVEDESIGNER")) {
            List<ChapterContentDto> chapterContentDtoList = chapters.stream()
                    .map(chapter -> new ChapterContentDto(
                            chapter.getTitle(),
                            chapter.getDescription(),
                            chapter.getNumber(),
                            chapter.getImage(),
                            chapter.getStatus()
                    ))
                    .toList();

            return chapterContentDtoList;
        }

        List<ChapterContentDto> chapterContentDtoList = chapters.stream()
                .filter(chapter -> "PUBLISHED".equals(chapter.getStatus().name()))
                .map(chapter -> new ChapterContentDto(
                        chapter.getTitle(),
                        chapter.getDescription(),
                        chapter.getNumber(),
                        chapter.getImage(),
                        chapter.getStatus()
                ))
                .toList();

        return chapterContentDtoList;
    }
}