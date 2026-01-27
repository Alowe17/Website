package com.example.Web_Service.model.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_progress")
public class UserProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "date_passage", nullable = false)
    private LocalDateTime datePassage = LocalDateTime.now();

    public UserProgress (int id, User user, Chapter chapter, LocalDateTime datePassage) {
        this.id = id;
        this.user = user;
        this.chapter = chapter;
        this.datePassage = datePassage;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public LocalDateTime getDatePassage() {
        return datePassage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public void setDatePassage(LocalDateTime datePassage) {
        this.datePassage = datePassage;
    }
}