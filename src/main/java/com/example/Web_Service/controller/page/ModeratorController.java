package com.example.Web_Service.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModeratorController {
    @GetMapping("/moderator")
    public String ModeratorPanel () {
        return "moderator/moderatorPanel";
    }
}