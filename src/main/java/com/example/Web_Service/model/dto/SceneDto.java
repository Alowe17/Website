package com.example.Web_Service.model.dto;

import java.util.List;

public class SceneDto {
    private String sceneId;
    private List<ChoiceDto> choices;
    private List<DialogDto> dialogs;

    public SceneDto (String sceneId, List<ChoiceDto> choices, List<DialogDto> dialogs) {
        this.sceneId = sceneId;
        this.choices = choices;
        this.dialogs = dialogs;
    }

    public String getSceneId() {
        return sceneId;
    }

    public List<ChoiceDto> getChoices() {
        return choices;
    }

    public List<DialogDto> getDialogs() {
        return dialogs;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public void setChoices(List<ChoiceDto> choices) {
        this.choices = choices;
    }

    public void setDialogs(List<DialogDto> dialogs) {
        this.dialogs = dialogs;
    }
}