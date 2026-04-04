package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.support.SupportMessageDto;
import com.example.Web_Service.model.dto.adminDto.support.request.SupportReplyDto;
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

    public MessageSupport createNewSupportMessage (SupportMessageDto supportMessageDto, User user) {
        MessageSupport messageSupport = new MessageSupport();
        messageSupport.setMessage(supportMessageDto.getMessage());
        messageSupport.setDate(supportMessageDto.getCreatedDate());
        messageSupport.setUser(user);
        messageSupport.setStatus(Status.NEW);

        return messageSupportRepository.save(messageSupport);
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

    public String validator (SupportReplyDto supportReplyDto) {
        if (supportReplyDto.getUsername().trim().isBlank()) {
            return "Невозможно найти пользователя обратившегося в поддержку!";
        }

        if (supportReplyDto.getDate() == null) {
            return "Невозможно найти дату обращения в поддержку!";
        }

        if (supportReplyDto.getStatus() == null) {
            return "Невозможно ответить на обращение без статуса!";
        }

        if (supportReplyDto.getAnswer().trim().isBlank()) {
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