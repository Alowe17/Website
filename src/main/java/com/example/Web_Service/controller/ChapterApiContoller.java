package com.example.Web_Service.controller;

import com.example.Web_Service.model.dto.ChapterDto;
import com.example.Web_Service.service.ChapterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChapterApiContoller {
    private ChapterService chapterService;

    public ChapterApiContoller (ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @GetMapping("/api/chapter/{slug}")
    public ChapterDto getChapter(@PathVariable String slug) {
        return chapterService.getChapter(slug);
    }
}