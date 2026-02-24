package com.example.Web_Service.model.dto.moderator.response;

import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.enums.Status;

import java.time.LocalDate;

public class SupportTicketAnswerDto {
    private String message;
    private User user;
    private Status status;
    private String answer;
    private LocalDate date;
    private User administrator;

    public SupportTicketAnswerDto(String message, User user, Status status, String answer, LocalDate date, User administrator) {
        this.message = message;
        this.user = user;
        this.status = status;
        this.answer = answer;
        this.date = date;
        this.administrator = administrator;
    }

    public SupportTicketAnswerDto() {}

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
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

    public void setUser(User user) {
        this.user = user;
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