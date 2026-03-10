package com.example.Web_Service.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_game_state")
public class UserGameState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private User user;
    @ManyToOne
    private Scene currentScene;

    public UserGameState (int id, User user, Scene currentScene) {
        this.id = id;
        this.user = user;
        this.currentScene = currentScene;
    }

    public UserGameState () {}

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }
}