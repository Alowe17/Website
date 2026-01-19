package com.example.Web_Service.model.dto;

public class ChoiceDto {
    private int id;
    private String text;
    private String nextSceneId;

    public ChoiceDto (int id, String text, String nextSceneId) {
        this.id = id;
        this.text = text;
        this.nextSceneId = nextSceneId;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getNextSceneId() {
        return nextSceneId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setNextSceneId(String nextSceneId) {
        this.nextSceneId = nextSceneId;
    }
}