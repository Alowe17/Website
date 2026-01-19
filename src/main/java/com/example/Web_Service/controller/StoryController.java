package com.example.Web_Service.controller;

import com.example.Web_Service.model.entity.Scene;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.service.StoryService;
import com.example.Web_Service.users.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StoryController {
    @GetMapping("/story")
    public String story (Authentication authentication, Model model) {
        if (authentication == null) {
            return "redirect:/login";
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();
        int level = user.getProgress_level();
        int xp = user.getProgress_xp();

        model.addAttribute("story1", 100);
        model.addAttribute("story2", level >= 2 ? (level > 2 ? 100 : xp) : 0);
        model.addAttribute("story3", level >= 3 ? (level > 3 ? 100 : xp) : 0);
        model.addAttribute("story4", 0);
        model.addAttribute("user", user);
        return "story";
    }

    @GetMapping("/story/game")
    public String game () {
        return "test";
    }
}