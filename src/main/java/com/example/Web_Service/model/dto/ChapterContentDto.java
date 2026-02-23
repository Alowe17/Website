package com.example.Web_Service.model.dto;

import com.example.Web_Service.model.enums.StatusGame;

public class ChapterContentDto {
    private String title;
    private String description;
    private int number;
    private String image;
    private StatusGame status;

    public ChapterContentDto(String title, String description, int number, String image, StatusGame status) {
        this.title = title;
        this.description = description;
        this.number = number;
        this.image = image;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getNumber() {
        return number;
    }

    public String getImage() {
        return image;
    }

    public StatusGame getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setStatus(StatusGame status) {
        this.status = status;
    }
}