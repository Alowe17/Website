package com.example.Web_Service.service;

import com.example.Web_Service.model.Choice;
import com.example.Web_Service.model.Scene;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StoryService {
    private final Map<String, Scene> sceneMap = new HashMap<String, Scene>();

    public StoryService () {
        sceneMap.put("cafe", new Scene(
                "cafe",
                List.of(
                        "Ты медленно открываешь глаза.",
                        "Вокруг темно и холодно."
                ),
                List.of(
                        new Choice("Осмотреться", "look"),
                        new Choice("Позвать на помощь", "shout")
                )
        ));

        sceneMap.put("look", new Scene(
                "look",
                List.of(
                        "Осмотревшись ничего полезного не увидел",
                        "Ситуация непростая"
                ),
                List.of(
                        new Choice("Вернуться обратно в кафе", "cafe"),
                        new Choice("Позвать на помощь", "shout")
                )
        ));
    }

    public Scene getScene (String sceneId) {
        return sceneMap.get(sceneId);
    }
}