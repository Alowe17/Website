package com.example.Web_Service.controller.page;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {
    @GetMapping("/register")
    public String register (Authentication authentication) {
        if (authentication != null) {
            return "index";
        }

        return "register";
    }
}