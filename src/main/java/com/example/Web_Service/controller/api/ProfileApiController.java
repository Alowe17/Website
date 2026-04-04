package com.example.Web_Service.controller.api;

import com.example.Web_Service.model.dto.user.UserUpdateDto;
import com.example.Web_Service.model.dto.user.UserProgressDto;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.service.ProfileService;
import com.example.Web_Service.service.RefreshTokenService;
import com.example.Web_Service.service.UserProgressService;
import com.example.Web_Service.users.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/profile")
public class ProfileApiController {
    private final UserProgressService userProgressService;
    private final ProfileService profileService;
    private final RefreshTokenService refreshTokenService;

    public ProfileApiController(UserProgressService userProgressService, ProfileService profileService, RefreshTokenService refreshTokenService) {
        this.userProgressService = userProgressService;
        this.profileService = profileService;
        this.refreshTokenService = refreshTokenService;
    }

    @GetMapping
    public ResponseEntity<UserProgressDto> getUserProgressDto (Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        if (user == null) {
            System.out.println("User is null");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(userProgressService.getUserProgressDto(user));
    }

    @PostMapping("/users")
    public ResponseEntity<?> updateUser (@Valid @RequestBody UserUpdateDto userUpdateDto, Authentication authentication) {
        String message = profileService.validateUser(userUpdateDto);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        if (message != null) {
            return ResponseEntity.badRequest().body(Map.of("message", message));
        }

        profileService.updateUserData(userUpdateDto, user);
        refreshTokenService.revokeAll(user);
        return ResponseEntity.ok().body(Map.of("message", "Данные успешно обновлены!"));
    }
}