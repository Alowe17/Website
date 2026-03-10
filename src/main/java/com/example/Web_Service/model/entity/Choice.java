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
    private String effectKey;
    private Integer effectValue;
    private String requiredKey;
    private Integer requiredMinValue;

    public Choice (int id, String text, Scene sceneFrom, Scene sceneTo, String effectKey, Integer effectValue, String requiredKey, Integer requiredMinValue) {
        this.id = id;
        this.text = text;
        this.sceneFrom = sceneFrom;
        this.sceneTo = sceneTo;
        this.effectKey = effectKey;
        this.effectValue = effectValue;
        this.requiredKey = requiredKey;
        this.requiredMinValue = requiredMinValue;
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

    public String getEffectKey() {
        return effectKey;
    }

    public Integer getEffectValue() {
        return effectValue;
    }

    public String getRequiredKey() {
        return requiredKey;
    }

    public Integer getRequiredMinValue() {
        return requiredMinValue;
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

    public void setEffectKey(String effectKey) {
        this.effectKey = effectKey;
    }

    public void setEffectValue(Integer effectValue) {
        this.effectValue = effectValue;
    }

    public void setRequiredKey(String requiredKey) {
        this.requiredKey = requiredKey;
    }

    public void setRequiredMinValue(Integer requiredMinValue) {
        this.requiredMinValue = requiredMinValue;
    }
}