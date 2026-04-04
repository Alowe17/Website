package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.support.SupportMessageDto;
import com.example.Web_Service.model.dto.moderator.response.SupportTicketAnswerDto;
import com.example.Web_Service.model.dto.moderator.response.SupportTicketNewDto;
import com.example.Web_Service.model.entity.MessageSupport;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.enums.Role;
import com.example.Web_Service.model.enums.Status;
import com.example.Web_Service.repository.MessageSupportRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class SupportService {
    private final MessageSupportRepository messageSupportRepository;

    public SupportService(MessageSupportRepository messageSupportRepository) {
        this.messageSupportRepository = messageSupportRepository;
    }

    public String createNewSupportMessage (SupportMessageDto supportMessageDto, User user) {
        Optional<MessageSupport> lastMessage = messageSupportRepository.findFirstByUserOrderByDateDesc(user);

        if (lastMessage.isPresent()) {
            Instant lastDate = lastMessage.get().getDate();
            Instant now = Instant.now();
            Duration duration = Duration.between(lastDate, now);

            if (duration.toMinutes() < 30 && user.getRole() != Role.ADMINISTRATOR) {
                long minutesLeft = 30 - duration.toMinutes();
                return "Вы уже отправляли обращение недавно. Пожалуйста, подождите еще " + minutesLeft + " мин. перед отправкой следующего.";
            }
        }

        MessageSupport messageSupport = new MessageSupport();
        messageSupport.setMessage(supportMessageDto.getMessage());
        messageSupport.setDate(Instant.now());
        messageSupport.setUser(user);
        messageSupport.setStatus(Status.NEW);

        messageSupportRepository.save(messageSupport);
        return null;
    }

    public List<com.example.Web_Service.model.dto.adminDto.support.response.SupportMessageDto> getListOldMessage (User user) {
        List<MessageSupport> messageSupportList = messageSupportRepository.findByUser(user);

        if (messageSupportList.isEmpty()) {
            return List.of();
        }

        List<com.example.Web_Service.model.dto.adminDto.support.response.SupportMessageDto> supportMessageDtoList = messageSupportList.stream()
                .map(support -> new com.example.Web_Service.model.dto.adminDto.support.response.SupportMessageDto(
                        support.getMessage(),
                        support.getUser(),
                        support.getStatus(),
                        support.getAnswer(),
                        support.getDate(),
                        support.getAdministrator()
                ))
                .toList();

        return supportMessageDtoList;
    }

    public List<com.example.Web_Service.model.dto.adminDto.support.response.SupportMessageDto> getListMessageSupportDto () {
        List<MessageSupport> messageSupportList = messageSupportRepository.findAll();

        if (messageSupportList.isEmpty()) {
            return List.of();
        }

        List<com.example.Web_Service.model.dto.adminDto.support.response.SupportMessageDto> getListSupportMessageDto = messageSupportList.stream()
                .map(support -> new com.example.Web_Service.model.dto.adminDto.support.response.SupportMessageDto(
                        support.getMessage(),
                        support.getUser(),
                        support.getStatus(),
                        support.getAnswer(),
                        support.getDate(),
                        support.getAdministrator()
                ))
                .toList();

        return getListSupportMessageDto;
    }

    public MessageSupport getMessageSupport (int id) {
        return messageSupportRepository.findById(id).orElse(null);
    }

    public void replyToMessageSave(MessageSupport messageSupport) {
        messageSupportRepository.save(messageSupport);
    }

    public List<SupportTicketNewDto> getListSupportTicketsNew () {
        List<MessageSupport> messageSupportList = messageSupportRepository.findAll();

        if (messageSupportList.isEmpty()) {
            return List.of();
        }

        List<SupportTicketNewDto> list = messageSupportList.stream()
                .filter(ticket -> "NEW".equals(ticket.getStatus().name()))
                .map(ticket -> new SupportTicketNewDto(
                        ticket.getId(),
                        ticket.getStatus(),
                        ticket.getMessage(),
                        ticket.getUser(),
                        ticket.getDate()
                ))
                .toList();

        return list;
    }

    public List<SupportTicketAnswerDto> getListSupportTicketsAnswer () {
        List<MessageSupport> messageSupportList = messageSupportRepository.findAll();

        if (messageSupportList.isEmpty()) {
            return List.of();
        }

        List<SupportTicketAnswerDto> list = messageSupportList.stream()
                .filter(ticket -> !"NEW".equals(ticket.getStatus().name()))
                .map(ticket -> new SupportTicketAnswerDto(
                        ticket.getMessage(),
                        ticket.getUser(),
                        ticket.getStatus(),
                        ticket.getAnswer(),
                        ticket.getDate(),
                        ticket.getAdministrator()
                ))
                .toList();

        return list;
    }

    public SupportTicketNewDto getSupportTicketNew (MessageSupport messageSupport) {
        if (messageSupport == null) {
            return null;
        }

        return new SupportTicketNewDto(
                messageSupport.getId(),
                messageSupport.getStatus(),
                messageSupport.getMessage(),
                messageSupport.getUser(),
                messageSupport.getDate()
        );
    }
}