package com.example.Web_Service.model.dto.moderator.response;

import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.enums.Status;

import java.time.LocalDate;

public class SupportTicketNewDto {
    private int id;
    private Status status;
    private String message;
    private User user;
    private LocalDate date;

    public SupportTicketNewDto (int id, Status status, String message, User user, LocalDate date) {
        this.id = id;
        this.status = status;
        this.message = message;
        this.user = user;
        this.date = date;
    }

    public SupportTicketNewDto () {}

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}