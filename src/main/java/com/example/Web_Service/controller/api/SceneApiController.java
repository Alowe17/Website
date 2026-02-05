package com.example.Web_Service.controller.api;

import com.example.Web_Service.model.dto.SceneDto;
import com.example.Web_Service.service.SceneService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SceneApiController {
    private final SceneService sceneService;

    public SceneApiController (SceneService sceneService) {
        this.sceneService = sceneService;
    }

    @GetMapping("/api/scenes/{sceneId}")
    public SceneDto getScene(@PathVariable String sceneId) {
        return sceneService.getSceneDto(sceneId);
    }
}