package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.SupportMessageRequestDto;
import com.example.Web_Service.model.dto.adminDto.SupportMessageResponseDto;
import com.example.Web_Service.model.dto.adminDto.SupportReplyRequestDto;
import com.example.Web_Service.model.dto.moderator.response.SupportTicketAnswerDto;
import com.example.Web_Service.model.dto.moderator.response.SupportTicketNewDto;
import com.example.Web_Service.model.entity.MessageSupport;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.enums.Status;
import com.example.Web_Service.repository.MessageSupportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupportService {
    private final MessageSupportRepository messageSupportRepository;

    public SupportService(MessageSupportRepository messageSupportRepository) {
        this.messageSupportRepository = messageSupportRepository;
    }

    public MessageSupport createNewSupportMessage (SupportMessageRequestDto supportMessageRequestDto, User user) {
        MessageSupport messageSupport = new MessageSupport();
        messageSupport.setMessage(supportMessageRequestDto.getMessage());
        messageSupport.setDate(supportMessageRequestDto.getCreatedDate());
        messageSupport.setUser(user);
        messageSupport.setStatus(Status.NEW);

        return messageSupportRepository.save(messageSupport);
    }

    public List<SupportMessageResponseDto> getListOldMessage (User user) {
        List<MessageSupport> messageSupportList = messageSupportRepository.findByUser(user);

        if (messageSupportList.isEmpty()) {
            return List.of();
        }

        List<SupportMessageResponseDto> supportMessageRequestDtoList = messageSupportList.stream()
                .map(support -> new SupportMessageResponseDto(
                        support.getMessage(),
                        support.getUser(),
                        support.getStatus(),
                        support.getAnswer(),
                        support.getDate(),
                        support.getAdministrator()
                ))
                .toList();

        return supportMessageRequestDtoList;
    }

    public List<SupportMessageResponseDto> getListMessageSupportDto () {
        List<MessageSupport> messageSupportList = messageSupportRepository.findAll();

        if (messageSupportList.isEmpty()) {
            return List.of();
        }

        List<SupportMessageResponseDto> getListSupportMessageDto = messageSupportList.stream()
                .map(support -> new SupportMessageResponseDto(
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

    public String validator (SupportReplyRequestDto supportReplyRequestDto) {
        if (supportReplyRequestDto.getUsername().trim().isBlank()) {
            return "Невозможно найти пользователя обратившегося в поддержку!";
        }

        if (supportReplyRequestDto.getDate() == null) {
            return "Невозможно найти дату обращения в поддержку!";
        }

        if (supportReplyRequestDto.getStatus() == null) {
            return "Невозможно ответить на обращение без статуса!";
        }

        if (supportReplyRequestDto.getAnswer().trim().isBlank()) {
            return "Невозможно ответить на обращение без ответа!";
        }

        return null;
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