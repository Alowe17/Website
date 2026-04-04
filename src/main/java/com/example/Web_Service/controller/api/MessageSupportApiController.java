package com.example.Web_Service.controller.api;

import com.example.Web_Service.model.dto.support.SupportMessageDto;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.service.SupportService;
import com.example.Web_Service.users.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/support")
public class MessageSupportApiController {
    private final SupportService supportService;

    public MessageSupportApiController(SupportService supportService) {
        this.supportService = supportService;
    }

    @PostMapping("/messages")
    public ResponseEntity<?> messageSent (@Valid @RequestBody SupportMessageDto supportMessageDto, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user =  customUserDetails.getUser();

        if (supportService.createNewSupportMessage(supportMessageDto, user) == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Увы, что-то пошло не так. Попробуйте обратиться позже."));
        }

        return ResponseEntity.ok().body(Map.of ("message", "Ваше обращение было успешно отправлено!"));
    }

    @GetMapping("/old-messages")
    public ResponseEntity<?> getOldMessage (Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user =  customUserDetails.getUser();

        List<com.example.Web_Service.model.dto.adminDto.support.response.SupportMessageDto> supportMessageDtos = supportService.getListOldMessage(user);

        if (supportMessageDtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Вы не обращались в поддержку ранее!"));
        }

        return ResponseEntity.ok().body(supportMessageDtos);
    }
}