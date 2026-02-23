package com.example.Web_Service.model.entity;

import com.example.Web_Service.model.enums.StatusGame;
import com.example.Web_Service.model.enums.Type;
import jakarta.persistence.*;

@Entity
@Table(name = "game_character")
public class GameCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private Type type;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    private StatusGame status;

    public GameCharacter (int id, String name, String imageUrl, Type type, String description, StatusGame status) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.type = type;
        this.description = description;
        this.status = status;
    }

    public GameCharacter() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Type getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public StatusGame getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(StatusGame status) {
        this.status = status;
    }
}