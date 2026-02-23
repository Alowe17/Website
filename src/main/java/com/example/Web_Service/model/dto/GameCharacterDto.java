package com.example.Web_Service.model.dto;

import com.example.Web_Service.model.enums.StatusGame;
import com.example.Web_Service.model.enums.Type;

public class GameCharacterDto {
    private String name;
    private String imageUrl;
    private Type type;
    private String description;
    private StatusGame status;

    public GameCharacterDto (String name, String imageUrl, Type type, String description, StatusGame status) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.type = type;
        this.description = description;
        this.status = status;
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