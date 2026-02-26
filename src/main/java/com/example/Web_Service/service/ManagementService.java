package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.adminDto.SupportMessageResponseDto;
import com.example.Web_Service.model.dto.moderator.response.SupportTicketAnswerDto;
import com.example.Web_Service.model.dto.moderator.response.SupportTicketNewDto;
import com.example.Web_Service.model.entity.MessageSupport;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.enums.Role;
import com.example.Web_Service.model.enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service
public class ManagementService {
    private final UserService userService;
    private final SupportService supportService;

    public ManagementService(UserService userService, SupportService supportService) {
        this.userService = userService;
        this.supportService = supportService;
    }

    public ResponseEntity<?> processReply (int id, User user) {
        MessageSupport messageSupport = supportService.getMessageSupport(id);

        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("message", "Вы не авторизованы!"));
        }

        if (messageSupport == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Не удалось найти обращение по ID: " + id));
        }

        SupportTicketNewDto supportTicketNewDto = supportService.getSupportTicketNew(messageSupport);

        if (user.getRole() == Role.MODERATOR) {
            if (messageSupport.getStatus() == Status.NEW && messageSupport.getAdministrator() == null) {
                log.info("Модератор {} получил доступ к обращению: {}! Дата обращения: {}", user.getUsername(), supportTicketNewDto.getId(), LocalDateTime.now());
                return ResponseEntity.ok().body(supportTicketNewDto);
            } else {
                log.info("Модератор {} пытался получить доступ к обращению: {}, но запрос был отклонен! Дата обращения: {}", user.getUsername(), supportTicketNewDto.getId(), LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "У вас недостаточно прав, чтобы изменять это обращение!"));
            }
        } else if (user.getRole() == Role.ADMINISTRATOR) {
            log.info("Администратор {} получил доступ к обращению: {}", user.getUsername(), supportTicketNewDto.getId());
            return ResponseEntity.ok().body(supportTicketNewDto);
        } else {
            log.info("Некий пользователь {} пытался получить доступ к обращению! Дата обращения: {}", user.getUsername(), LocalDateTime.now());
            return ResponseEntity.status(403).body(Map.of("message", "У вас недостаточно прав, чтобы посещать данную страницу!"));
        }
    }

    public ResponseEntity<?> replyMessage (int id, com.example.Web_Service.model.dto.moderator.request.SupportTicketNewDto supportTicketNewDto, User user) {
        MessageSupport messageSupport = supportService.getMessageSupport(id);

        if (messageSupport == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Не удалось найти обращение по ID: " + id));
        }

        if (user.getRole() == Role.MODERATOR) {
            if (messageSupport.getStatus() == Status.NEW && messageSupport.getAdministrator() == null) {
                log.info("Модератор {} дал ответ на обращение: {}! Дата обращения: {}", user.getUsername(), supportTicketNewDto.getId(), LocalDateTime.now());
                return saveTicket(messageSupport, supportTicketNewDto, user);
            } else {
                log.info("Модератор {} попытался дать ответ на обращение: {}! Дата обращения: {}", user.getUsername(), supportTicketNewDto.getId(), LocalDateTime.now());
                return ResponseEntity.status(403).body(Map.of("message", "У вас недостаточно прав, чтобы изменять это обращение!"));
            }
        } else if (user.getRole() == Role.ADMINISTRATOR) {
            log.info("Администратор {} дал ответ на обращение: {}! Дата обращения: {}", user.getUsername(), supportTicketNewDto.getId(), LocalDateTime.now());
            return saveTicket(messageSupport, supportTicketNewDto, user);
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    public ResponseEntity<?> saveTicket (MessageSupport messageSupport, com.example.Web_Service.model.dto.moderator.request.SupportTicketNewDto supportTicketNewDto, User user) {
        messageSupport.setStatus(supportTicketNewDto.getStatus());
        messageSupport.setAdministrator(user);
        messageSupport.setAnswer(supportTicketNewDto.getAnswer());

        supportService.replyToMessageSave(messageSupport);
        return ResponseEntity.ok().body(Map.of("message", "Был успешно дан ответ на обращение!"));
    }
}