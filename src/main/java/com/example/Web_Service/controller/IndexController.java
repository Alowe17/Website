package com.example.Web_Service.controller;

import com.example.Web_Service.model.User;
import com.example.Web_Service.users.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/index")
    public String indexPage(Model model, Authentication authentication ) {
        if (authentication != null &&  authentication.isAuthenticated()) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = customUserDetails.getUser();
            model.addAttribute("user", user);
        }
        return "index";
    }
}