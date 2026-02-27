package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.*;
import com.example.Web_Service.model.entity.Chapter;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.entity.UserProgress;
import com.example.Web_Service.model.enums.Role;
import com.example.Web_Service.model.enums.Status;
import com.example.Web_Service.model.enums.StatusGame;
import com.example.Web_Service.repository.ChapterRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChapterService {
    private final ChapterRepository chapterRepository;
    private final UserProgressService userProgressService;

    public ChapterService (ChapterRepository chapterRepository, UserProgressService userProgressService) {
        this.chapterRepository = chapterRepository;
        this.userProgressService = userProgressService;
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
            return List.of();
        }

        if (user.getRole() == Role.ADMINISTRATOR ||
                user.getRole() == Role.TESTER ||
                user.getRole() == Role.NARRATIVEDESIGNER) {
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
                .filter(chapter ->chapter.getStatus() == StatusGame.PUBLISHED)
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