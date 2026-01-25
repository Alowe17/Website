package com.example.Web_Service.service;

import com.example.Web_Service.model.entity.Choice;
import com.example.Web_Service.model.entity.Scene;
import com.example.Web_Service.repository.ChoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChoiceService {
    private final ChoiceRepository choiceRepository;

    public ChoiceService (ChoiceRepository choiceRepository) {
        this.choiceRepository = choiceRepository;
    }

    public List<Choice> getChoice (Scene scene) {
        return choiceRepository.findBySceneFrom(scene);
    }
}