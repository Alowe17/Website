package com.example.Web_Service.model;

import java.util.List;

public class Scene {
    private String id;
    private List<String> dialogs;
    private List<Choice> choices;

    public Scene (String id, List<String> dialogs, List<Choice> choices) {
        this.id = id;
        this.dialogs = dialogs;
        this.choices = choices;
    }

    public String getId() {
        return id;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public List<String> getDialogs() {
        return dialogs;
    }
}