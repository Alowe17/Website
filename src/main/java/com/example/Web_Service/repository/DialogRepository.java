package com.example.Web_Service.repository;

import com.example.Web_Service.model.entity.Dialog;
import com.example.Web_Service.model.entity.Scene;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DialogRepository extends JpaRepository<Dialog, Integer> {
    List<Dialog> findByScene(Scene scene);
}