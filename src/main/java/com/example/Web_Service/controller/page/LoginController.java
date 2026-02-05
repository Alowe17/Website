package com.example.Web_Service.controller.page;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginPage (Authentication authentication) {
        if (authentication != null) {
            return "redirect:/index";
        }

        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDeniedPage() {
        return "accessDenied";
    }
}