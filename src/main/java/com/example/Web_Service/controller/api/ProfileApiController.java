package com.example.Web_Service.controller.api;

import com.example.Web_Service.model.dto.UserDto;
import com.example.Web_Service.model.dto.UserProgressDto;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.service.UserProgressService;
import com.example.Web_Service.users.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileApiController {
    private final UserProgressService userProgressService;

    public ProfileApiController(UserProgressService userProgressService) {
        this.userProgressService = userProgressService;
    }

    @GetMapping("/api/profile")
    public UserProgressDto getUserProgressDto (Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();
        return userProgressService.getUserProgressDto(user);
    }
}