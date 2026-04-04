package com.example.Web_Service.model.entity;

import com.example.Web_Service.model.enums.Status;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@Entity
@Table(name = "message_support")
public class MessageSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "message", columnDefinition = "TEXT", nullable = false)
    private String message;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;
    @Column(name = "date", nullable = false)
    private Instant date = Instant.now();
    @Column(name = "answer", columnDefinition = "TEXT")
    private String answer;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "administrator_id")
    private User administrator;

    public MessageSupport (int id, String message, Status status, Instant date, String answer, User user, User administrator) {
        this.id = id;
        this.message = message;
        this.status = status;
        this.date = date;
        this.answer = answer;
        this.user = user;
        this.administrator = administrator;
    }

    public MessageSupport() {}

    public void setId(int id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAdministrator(User administrator) {
        this.administrator = administrator;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }

    public String getAnswer() {
        return answer;
    }

    public Instant getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public User getAdministrator() {
        return administrator;
    }
}