package com.example.Web_Service.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "scene")
public class Scene {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String sceneId;
    @OneToMany(mappedBy = "sceneFrom", cascade = CascadeType.ALL)
    private List<Choice> choices;
    @OneToMany(mappedBy = "scene", cascade = CascadeType.ALL)
    private List<Dialog> dialogs;
    @ManyToOne
    private Chapter chapter;

    public Scene(int id, String sceneId, List<Choice> choices, List<Dialog> dialogs, Chapter chapter) {
        this.id = id;
        this.sceneId = sceneId;
        this.choices = choices;
        this.dialogs = dialogs;
        this.chapter = chapter;
    }

    public Scene() {}

    public int getId() {
        return id;
    }

    public String getSceneId() {
        return sceneId;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public List<Dialog> getDialogs() {
        return dialogs;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public void setDialogs(List<Dialog> dialogs) {
        this.dialogs = dialogs;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }
}