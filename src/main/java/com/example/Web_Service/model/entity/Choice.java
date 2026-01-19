package com.example.Web_Service.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "choice")
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String text;
    @ManyToOne
    private Scene sceneFrom;
    @ManyToOne
    private Scene sceneTo;

    public Choice (int id, String text, Scene sceneFrom, Scene sceneTo) {
        this.id = id;
        this.text = text;
        this.sceneFrom = sceneFrom;
        this.sceneTo = sceneTo;
    }

    public Choice() {}

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Scene getSceneFrom() {
        return sceneFrom;
    }

    public Scene getSceneTo() {
        return sceneTo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSceneFrom(Scene sceneFrom) {
        this.sceneFrom = sceneFrom;
    }

    public void setSceneTo(Scene sceneTo) {
        this.sceneTo = sceneTo;
    }
}