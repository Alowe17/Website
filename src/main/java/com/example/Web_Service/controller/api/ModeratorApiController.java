package com.example.Web_Service.controller.api;

import com.example.Web_Service.model.dto.moderator.response.SupportTicketAnswerDto;
import com.example.Web_Service.model.dto.moderator.response.SupportTicketNewDto;
import com.example.Web_Service.service.SupportService;
import com.example.Web_Service.users.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ModeratorApiController {
    private final SupportService supportService;

    public ModeratorApiController (SupportService supportService) {
        this.supportService = supportService;
    }

    @GetMapping("/api/moderator")
    public ResponseEntity<?> getModerator () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        if (customUserDetails.getUser() == null) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok().body(customUserDetails.getUser().getUsername());
    }

    @GetMapping("/api/moderator/support-tickets/new")
    public ResponseEntity<?> getSupportList () {
        List<SupportTicketNewDto> list = supportService.getListSupportTicketsNew();

        if (list.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/api/moderator/support-tickets/answered")
    public ResponseEntity<?> getSupportListAnswered () {
        List<SupportTicketAnswerDto> list = supportService.getListSupportTicketsAnswer();

        if (list.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok().body(list);
    }
}