package com.example.Web_Service.controller.page;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/index")
    public String indexPage(Authentication authentication ) {
        if (authentication == null) {
            return "login";
        }

        return "index";
    }
}