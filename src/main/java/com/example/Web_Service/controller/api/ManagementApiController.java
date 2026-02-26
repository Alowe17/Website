package com.example.Web_Service.controller.api;

import com.example.Web_Service.model.dto.adminDto.SupportReplyResponseDto;
import com.example.Web_Service.model.dto.moderator.request.SupportTicketNewDto;
import com.example.Web_Service.model.dto.moderator.response.SupportTicketAnswerDto;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.enums.Role;
import com.example.Web_Service.service.ManagementService;
import com.example.Web_Service.users.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class ManagementApiController {
    private final ManagementService managementService;

    public ManagementApiController(ManagementService managementService) {
        this.managementService = managementService;
    }

    @GetMapping("/api/management")
    public ResponseEntity<?> getMessageWelcome () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        if (user.getRole() == Role.ADMINISTRATOR) {
            return ResponseEntity.ok().body("Администратор " + user.getUsername());
        } else if (user.getRole() == Role.MODERATOR) {
            return ResponseEntity.ok().body("Модератор " + user.getUsername());
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @GetMapping("/api/management/load-message/{id}")
    public ResponseEntity<?> getSupportMessage (@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return managementService.processReply(id, customUserDetails.getUser());
    }

    @PostMapping("/api/management/reply-message/{id}")
    public ResponseEntity<?> replyMessage (@PathVariable int id, @Valid @RequestBody SupportTicketNewDto supportTicketNewDto, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return managementService.replyMessage(id, supportTicketNewDto, customUserDetails.getUser());
    }
}