package com.example.Web_Service.controller.api;

import com.example.Web_Service.model.dto.ChapterContentDto;
import com.example.Web_Service.service.ChapterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexApiController {
    private final ChapterService chapterService;

    public IndexApiController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @GetMapping("/api/chapter-list")
    public ResponseEntity<List<ChapterContentDto>> getAllChapters () {
        List<ChapterContentDto> list = chapterService.getListChapterContentDto();

        return list != null ? ResponseEntity.ok().body(list) : ResponseEntity.notFound().build();
    }
}