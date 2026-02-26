package com.example.Web_Service.controller.api;

import com.example.Web_Service.model.dto.moderator.response.SupportTicketAnswerDto;
import com.example.Web_Service.model.dto.moderator.response.SupportTicketNewDto;
import com.example.Web_Service.service.SupportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ModeratorApiController {
    private final SupportService supportService;

    public ModeratorApiController (SupportService supportService) {
        this.supportService = supportService;
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