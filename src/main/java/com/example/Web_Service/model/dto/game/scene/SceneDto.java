package com.example.Web_Service.model.dto.game.scene;

import com.example.Web_Service.model.dto.DialogDto;
import com.example.Web_Service.model.dto.game.choice.ChoiceDto;
import com.example.Web_Service.model.enums.SceneType;

import java.util.List;

public class SceneDto {
    private String sceneId;
    private List<ChoiceDto> choiceDtoList;
    private List<DialogDto> dialogDtoList;
    private SceneType sceneType;

    public SceneDto (String sceneId, List<ChoiceDto> choiceDtoList, List<DialogDto> dialogDtoList, SceneType sceneType) {
        this.sceneId = sceneId;
        this.choiceDtoList = choiceDtoList;
        this.dialogDtoList = dialogDtoList;
        this.sceneType = sceneType;
    }

    public SceneDto() {}

    public String getSceneId() {
        return sceneId;
    }

    public List<ChoiceDto> getChoiceDtoList() {
        return choiceDtoList;
    }

    public List<DialogDto> getDialogDtoList() {
        return dialogDtoList;
    }

    public SceneType getSceneType() {
        return sceneType;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public void setChoiceDtoList(List<ChoiceDto> choiceDtoList) {
        this.choiceDtoList = choiceDtoList;
    }

    public void setDialogDtoList(List<DialogDto> dialogDtoList) {
        this.dialogDtoList = dialogDtoList;
    }

    public void setSceneType(SceneType sceneType) {
        this.sceneType = sceneType;
    }
}