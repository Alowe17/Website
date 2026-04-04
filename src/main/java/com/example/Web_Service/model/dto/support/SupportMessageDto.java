package com.example.Web_Service.model.dto.support;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.Instant;

public class SupportMessageDto {
    @NotBlank(message = "Нельзя отправлять пустой запрос в поддержку!")
    private String message;
    @PastOrPresent(message = "Дата обращения не может быть в будущем!")
    private Instant createdDate;

    public SupportMessageDto(String message, Instant createdDate) {
        this.message = message;
        this.createdDate = createdDate;
    }

    public String getMessage() {
        return message;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}