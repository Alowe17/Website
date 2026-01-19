package com.example.Web_Service.model.dto;

public class DialogDto {
    private String text;
    private GameCharacterDto gameCharacterDto;

    public DialogDto (String text, GameCharacterDto gameCharacterDto) {
        this.text = text;
        this.gameCharacterDto = gameCharacterDto;
    }

    public String getText() {
        return text;
    }

    public GameCharacterDto getGameCharacterDto() {
        return gameCharacterDto;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setGameCharacterDto(GameCharacterDto gameCharacterDto) {
        this.gameCharacterDto = gameCharacterDto;
    }
}