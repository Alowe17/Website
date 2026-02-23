package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.ChoiceDto;
import com.example.Web_Service.model.dto.DialogDto;
import com.example.Web_Service.model.dto.GameCharacterDto;
import com.example.Web_Service.model.dto.SceneDto;
import com.example.Web_Service.model.entity.Scene;
import com.example.Web_Service.repository.SceneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SceneService {
    private final SceneRepository sceneRepository;
    private final ChoiceService choiceService;
    private final DialogService dialogService;

    public SceneService (SceneRepository sceneRepository, ChoiceService choiceService, DialogService dialogService) {
        this.sceneRepository = sceneRepository;
        this.choiceService = choiceService;
        this.dialogService = dialogService;
    }

    public Scene getScene (String sceneId) {
        return sceneRepository.findBySceneId(sceneId).orElse(null);
    }

    public SceneDto getSceneDto (String sceneId) {
        Scene scene = sceneRepository.findBySceneId(sceneId).orElseThrow(() -> new RuntimeException("Scene not found"));

        List<ChoiceDto> choiceDtos = choiceService.getChoice(scene)
                .stream()
                .map(choice -> new ChoiceDto(
                        choice.getId(),
                        choice.getText(),
                        choice.getSceneTo().getSceneId()
                ))
                .toList();

        List<DialogDto> dialogDtos = dialogService.getDialogByScene(scene)
                .stream()
                .map(dialog -> new DialogDto(
                        dialog.getText(),
                        new GameCharacterDto(
                                dialog.getCharacter().getName(),
                                dialog.getCharacter().getImageUrl(),
                                dialog.getCharacter().getType(),
                                dialog.getCharacter().getDescription(),
                                dialog.getCharacter().getStatus()
                        )
                ))
                .toList();

        return new SceneDto(sceneId, choiceDtos, dialogDtos);
    }
}