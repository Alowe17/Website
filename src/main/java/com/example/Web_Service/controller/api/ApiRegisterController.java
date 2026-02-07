package com.example.Web_Service.controller.api;

import com.example.Web_Service.model.dto.RegisterRequestDto;
import com.example.Web_Service.model.entity.Chapter;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.service.ChapterService;
import com.example.Web_Service.service.RegisterService;
import com.example.Web_Service.service.UserProgressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ApiRegisterController {
    private final RegisterService registerService;
    private final UserProgressService userProgressService;
    private final ChapterService chapterService;

    public ApiRegisterController(RegisterService registerService, UserProgressService userProgressService, ChapterService chapterService) {
        this.registerService = registerService;
        this.userProgressService = userProgressService;
        this.chapterService = chapterService;
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> registerUser (@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        String errorMessage = registerService.validateUser(registerRequestDto);

        if (errorMessage != null) {
            return ResponseEntity.badRequest().body(Map.of("message", errorMessage));
        }

        User newUser = registerService.register(registerRequestDto);

        Chapter chapter = chapterService.getChapter(1);

        userProgressService.createUserProgress(newUser, chapter);
        return ResponseEntity.ok().body(Map.of("message", "Аккаунт успешно зарегистрирован!"));
    }
}