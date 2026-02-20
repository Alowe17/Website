package com.example.Web_Service.controller.api;

import com.example.Web_Service.model.dto.SupportMessageRequestDto;
import com.example.Web_Service.model.dto.adminDto.SupportMessageResponseDto;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.service.SupportService;
import com.example.Web_Service.users.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class MessageSupportApiController {
    private final SupportService supportService;

    public MessageSupportApiController(SupportService supportService) {
        this.supportService = supportService;
    }

    @PostMapping("/api/support/message-sent")
    public ResponseEntity<?> messageSent (@Valid @RequestBody SupportMessageRequestDto supportMessageRequestDto, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user =  customUserDetails.getUser();

        if (supportService.createNewSupportMessage(supportMessageRequestDto, user) == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Увы, что-то пошло не так. Попробуйте обратиться позже."));
        }

        return ResponseEntity.ok().body(Map.of ("message", "Ваше обращение было успешно отправлено!"));
    }

    @GetMapping("/api/old-message-support")
    public ResponseEntity<?> getOldMessage (Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user =  customUserDetails.getUser();

        List<SupportMessageResponseDto> supportMessageResponseDtos = supportService.getListOldMessage(user);

        if (supportMessageResponseDtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Вы не обращались в поддержку ранее!"));
        }

        return ResponseEntity.ok().body(supportMessageResponseDtos);
    }
}