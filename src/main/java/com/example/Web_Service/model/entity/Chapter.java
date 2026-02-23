package com.example.Web_Service.model.entity;

import com.example.Web_Service.model.enums.StatusGame;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "chapter")
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private int number;
    private String slug;
    private String image;
    private String sceneId;
    @Enumerated(EnumType.STRING)
    private StatusGame status;
    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Scene> scenes;

    public Chapter (int id, String title, String description, int number, String slug, String image, String sceneId, StatusGame status, List<Scene> scenes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.number = number;
        this.slug = slug;
        this.image = image;
        this.sceneId = sceneId;
        this.status = status;
    }

    public Chapter () {}

    public int getId() {
        return id;
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

    public List<Scene> getScenes() {
        return scenes;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setScenes(List<Scene> scenes) {
        this.scenes = scenes;
    }
}