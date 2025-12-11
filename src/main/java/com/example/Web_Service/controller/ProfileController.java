package com.example.Web_Service.controller;

import com.example.Web_Service.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import com.example.Web_Service.users.CustomUserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ProfileController {
    @GetMapping("/profile")
    public String profile (Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = customUserDetails.getUser();
            model.addAttribute("user", user);
        }

        return "profile";
    }
}