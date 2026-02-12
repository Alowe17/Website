package com.example.Web_Service.controller.page;

import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.users.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StoryController {
    @GetMapping("/story")
    public String story () {
        return "story";
    }

    @GetMapping("/story/game")
    public String game () {
        return "test";
    }
}