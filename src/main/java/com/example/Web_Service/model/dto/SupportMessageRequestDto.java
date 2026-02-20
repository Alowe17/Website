package com.example.Web_Service.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public class SupportMessageRequestDto {
    @NotBlank(message = "Нельзя отправлять пустой запрос в поддержку!")
    private String message;
    @PastOrPresent(message = "Дата обращения не может быть в будущем!")
    private LocalDate createdDate;

    public SupportMessageRequestDto(String message, LocalDate createdDate) {
        this.message = message;
        this.createdDate = createdDate;
    }

    public String getMessage() {
        return message;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}