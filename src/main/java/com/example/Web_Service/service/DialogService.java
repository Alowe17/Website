package com.example.Web_Service.service;

import com.example.Web_Service.model.entity.Dialog;
import com.example.Web_Service.model.entity.Scene;
import com.example.Web_Service.repository.ChoiceRepository;
import com.example.Web_Service.repository.DialogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DialogService {
    private final DialogRepository dialogRepository;

    public DialogService (DialogRepository dialogRepository) {
        this.dialogRepository = dialogRepository;
    }

    public List<Dialog> getDialogByScene (Scene scene) {
        return dialogRepository.findByScene(scene);
    }
}