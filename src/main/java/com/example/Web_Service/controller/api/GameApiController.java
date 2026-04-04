package com.example.Web_Service.controller.api;

import com.example.Web_Service.model.dto.game.dish.request.DishBuyDto;
import com.example.Web_Service.model.dto.game.user.UserGameDto;
import com.example.Web_Service.model.entity.Chapter;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.entity.UserProgress;
import com.example.Web_Service.service.ChapterService;
import com.example.Web_Service.service.GameService;
import com.example.Web_Service.service.UserProgressService;
import com.example.Web_Service.users.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/game")
public class GameApiController {
    private final GameService gameService;
    private final ChapterService chapterService;
    private final UserProgressService userProgressService;

    public GameApiController (GameService gameService, ChapterService chapterService, UserProgressService userProgressService) {
        this.gameService = gameService;
        this.chapterService = chapterService;
        this.userProgressService = userProgressService;
    }

    @GetMapping
    public ResponseEntity<?> welcome (Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("message", "Вы не авторизованы!"));
        }

        UserGameDto userGameDto = new UserGameDto(user.getName(), user.getUsername(), user.getBalance(), user.getRole());

        return ResponseEntity.ok().body(userGameDto);
    }


    @GetMapping("/chapters/cafe")
    public ResponseEntity<?> startGame (Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("message", "Вы не авторизованы!"));
        }

        UserProgress userProgress = userProgressService.getUserProgress(user);
        Chapter chapter = chapterService.getChapterSlug("cafe");
        return gameService.startChapterGame(chapter, userProgress);
    }

    @GetMapping("/menu/dishes")
    public ResponseEntity<?> menuDish (Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("message", "Вы не авторизованы!"));
        }

        return gameService.listDish();
    }

    @PostMapping("/scenes/{choiceId}")
    public ResponseEntity<?> nextScene (@PathVariable int choiceId, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("message", "Вы не авторизованы!"));
        }

        return gameService.choose(user, choiceId);
    }

    @PostMapping("/dishes")
    public ResponseEntity<?> buyDish ( @RequestBody DishBuyDto dishBuyDto, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("message", "Вы не авторизованы!"));
        }

        return gameService.buyDish(user, dishBuyDto);
    }
}