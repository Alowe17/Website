package com.example.Web_Service.controller;

import com.example.Web_Service.model.Choice;
import com.example.Web_Service.model.Scene;
import com.example.Web_Service.service.StoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SceneApiController {
    private final StoryService storyService;

    public SceneApiController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping("/story/game/{sceneId}")
    public Scene getScene(@PathVariable String sceneId) {
        return storyService.getScene(sceneId);
    }
}