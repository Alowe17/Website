package com.example.Web_Service.model.dto;

import java.time.LocalDateTime;

public class UserProgressDto {
    private UserDto userDto;
    private ChapterDto chapterDto;
    private LocalDateTime datePassage;

    public UserProgressDto (UserDto userDto, ChapterDto chapterDto, LocalDateTime datePassage) {
        this.userDto = userDto;
        this.chapterDto = chapterDto;
        this.datePassage = datePassage;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public ChapterDto getChapterDto() {
        return chapterDto;
    }

    public LocalDateTime getDatePassage() {
        return datePassage;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public void setChapterDto(ChapterDto chapterDto) {
        this.chapterDto = chapterDto;
    }

    public void setDatePassage(LocalDateTime datePassage) {
        this.datePassage = datePassage;
    }
}