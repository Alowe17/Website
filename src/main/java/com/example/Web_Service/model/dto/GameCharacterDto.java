package com.example.Web_Service.model.dto;

import com.example.Web_Service.model.enums.Type;

public class GameCharacterDto {
    private String name;
    private String imageUrl;
    private Type type;

    public GameCharacterDto (String name, String imageUrl, Type type) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.type = type;
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