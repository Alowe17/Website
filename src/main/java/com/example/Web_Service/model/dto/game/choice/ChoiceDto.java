package com.example.Web_Service.model.dto.game.choice;

public class ChoiceDto {
    private int id;
    private String text;
    private int nextSceneId;

    public ChoiceDto (int id, String text, int nextSceneId) {
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

    public int getNextSceneId() {
        return nextSceneId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setNextSceneId(int nextSceneId) {
        this.nextSceneId = nextSceneId;
    }
}