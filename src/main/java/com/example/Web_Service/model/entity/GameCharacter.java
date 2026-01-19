package com.example.Web_Service.model.entity;

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

    public GameCharacter (int id, String name, String imageUrl, Type type) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.type = type;
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
}