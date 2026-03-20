package com.example.Web_Service.controller.api;

import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.service.RewardService;
import com.example.Web_Service.users.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/promo-code")
public class RewardPageApiController {
    private final RewardService rewardService;

    public RewardPageApiController (RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @GetMapping("/{url}")
    public ResponseEntity<?> getRewardPage (@PathVariable String url) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        if (rewardService.checkingAccessRights(user, url)) {
            log.info("✓ Доступ разрешен для пользователя {} к странице: {}", user.getUsername(), url);
            return ResponseEntity.ok().body(Map.of("message", "Добро пожаловать, " + user.getUsername() + "!"));
        }

        log.warn("Доступ запрещен для пользователя {} к странице {}", user.getUsername(), url);
        return ResponseEntity.status(403).body(Map.of("message", "У вас недостаточно прав!"));
    }
}