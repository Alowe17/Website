package com.example.Web_Service.model.dto;

import com.example.Web_Service.model.enums.StatusGame;

import java.util.List;

public class ChapterDto {
    private String title;
    private String description;
    private int number;
    private String slug;
    private String image;
    private String sceneId;
    private StatusGame status;

    public ChapterDto (String title, String description, int number, String slug, String image, String sceneId, StatusGame status) {
        this.title = title;
        this.description = description;
        this.number = number;
        this.slug = slug;
        this.image = image;
        this.sceneId = sceneId;
        this.status = status;
    }

    public ChapterDto (int number) {
        this.number = number;
    }

    public ChapterDto () {}

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getNumber() {
        return number;
    }

    public String getSlug() {
        return slug;
    }

    public String getImage() {
        return image;
    }

    public String getSceneId() {
        return sceneId;
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

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public void setStatus(StatusGame status) {
        this.status = status;
    }
}