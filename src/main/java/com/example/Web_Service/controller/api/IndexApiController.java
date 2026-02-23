package com.example.Web_Service.controller.api;

import com.example.Web_Service.model.dto.ChapterContentDto;
import com.example.Web_Service.model.dto.GameCharacterDto;
import com.example.Web_Service.model.entity.GameCharacter;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.service.ChapterService;
import com.example.Web_Service.service.GameCharacterService;
import com.example.Web_Service.users.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexApiController {
    private final ChapterService chapterService;
    private final GameCharacterService gameCharacterService;

    public IndexApiController(ChapterService chapterService, GameCharacterService gameCharacterService) {
        this.chapterService = chapterService;
        this.gameCharacterService = gameCharacterService;
    }

    @GetMapping("/api/index/chapters-list")
    public ResponseEntity<List<ChapterContentDto>> getAllChapters () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        List<ChapterContentDto> list = chapterService.getChapters(user);

        if (list == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/api/index/characters-list")
    public ResponseEntity<List<GameCharacterDto>> getAllCharacters () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        List<GameCharacterDto> list = gameCharacterService.getGameCharacters(user);

        if (list == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(list);
    }
}