package com.example.Web_Service.model;

public class Choice {
    private String text;
    private String nextIdScene;

    public Choice (String text, String nextIdScene) {
        this.text = text;
        this.nextIdScene = nextIdScene;
    }

    public String getText() {
        return text;
    }

    public String getNextIdScene() {
        return nextIdScene;
    }
}