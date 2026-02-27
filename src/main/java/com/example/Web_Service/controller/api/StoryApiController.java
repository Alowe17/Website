package com.example.Web_Service.controller.api;

import com.example.Web_Service.model.dto.ChapterContentDto;
import com.example.Web_Service.model.dto.UserProgressDto;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.service.ChapterService;
import com.example.Web_Service.service.UserProgressService;
import com.example.Web_Service.users.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class StoryApiController {
    private final ChapterService chapterService;
    private final UserProgressService userProgressService;

    public StoryApiController (ChapterService chapterService, UserProgressService userProgressService) {
        this.chapterService = chapterService;
        this.userProgressService = userProgressService;
    }

    @GetMapping("/api/story/chapters-list")
    public ResponseEntity<?> getChapterList () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        List<ChapterContentDto> list = chapterService.getChapters(user);

        if (list.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Главы не были найдены! Попробуйте позже!"));
        }

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/api/story/users-progress")
    public ResponseEntity<?> getUserProgress () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        UserProgressDto userProgressDto = userProgressService.getUserProgressDto(user);

        if (userProgressDto == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Не удалось найти прогресс пользователя!"));
        }

        return ResponseEntity.ok().body(userProgressDto);
    }
}