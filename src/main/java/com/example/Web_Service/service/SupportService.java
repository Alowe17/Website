package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.SupportMessageRequestDto;
import com.example.Web_Service.model.dto.SupportMessageResponseDto;
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
}