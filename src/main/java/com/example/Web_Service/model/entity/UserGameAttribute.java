package com.example.Web_Service.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_game_attribute")
public class UserGameAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false)
    private String effectKey;
    @Column(nullable = false)
    private int effectValue;

    public UserGameAttribute (int id, User user, String effectkey, int effectValue) {
        this.id = id;
        this.user = user;
        this.effectKey = effectkey;
        this.effectValue = effectValue;
    }

    public UserGameAttribute () {}

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getEffectKey() {
        return effectKey;
    }

    public int getEffectValue() {
        return effectValue;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setEffectKey(String effectKey) {
        this.effectKey = effectKey;
    }

    public void setEffectValue(int effectValue) {
        this.effectValue = effectValue;
    }
}