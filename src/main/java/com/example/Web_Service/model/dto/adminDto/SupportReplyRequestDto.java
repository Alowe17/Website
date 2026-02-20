package com.example.Web_Service.model.dto.adminDto;

import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.enums.Status;

import java.time.LocalDate;

public class SupportReplyRequestDto {
    private String message;
    private String username;
    private Status status;
    private String answer;
    private LocalDate date;
    private User administrator;

    public SupportReplyRequestDto (String message, String username, Status status, String answer, LocalDate date, User administrator) {
        this.message = message;
        this.username = username;
        this.status = status;
        this.answer = answer;
        this.date = date;
        this.administrator = administrator;
    }

    public SupportReplyRequestDto () {}

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public Status getStatus() {
        return status;
    }

    public String getAnswer() {
        return answer;
    }

    public LocalDate getDate() {
        return date;
    }

    public User getAdministrator() {
        return administrator;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setAdministrator(User administrator) {
        this.administrator = administrator;
    }
}