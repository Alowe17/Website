package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.ChapterDto;
import com.example.Web_Service.model.dto.UserDto;
import com.example.Web_Service.model.dto.UserProgressDto;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.entity.UserProgress;
import com.example.Web_Service.repository.UserProgressRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProgressService {
    private final UserProgressRepository userProgressRepository;

    public  UserProgressService(UserProgressRepository userProgressRepository) {
        this.userProgressRepository = userProgressRepository;
    }

    public UserProgressDto getUserProgressDto (User user) {
        UserProgress userProgress = userProgressRepository.findByUser(user).orElseThrow(() -> new RuntimeException("UserProgress not found"));

        return new UserProgressDto(
                new UserDto(
                        userProgress.getUser().getName(),
                        userProgress.getUser().getUsername(),
                        userProgress.getUser().getEmail(),
                        userProgress.getUser().getPhone(),
                        userProgress.getUser().getBirthdate(),
                        userProgress.getUser().getRole(),
                        userProgress.getUser().getBalance()
                ),

                new ChapterDto(
                        userProgress.getChapter().getTitle(),
                        userProgress.getChapter().getDescription(),
                        userProgress.getChapter().getNumber(),
                        userProgress.getChapter().getSlug(),
                        userProgress.getChapter().getImage(),
                        userProgress.getChapter().getSceneId()
                ),

                userProgress.getDatePassage()
        );
    }
}