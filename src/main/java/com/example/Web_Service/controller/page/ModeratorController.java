package com.example.Web_Service.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ModeratorController {
    @GetMapping("/moderator")
    public String ModeratorPanel () {
        return "moderator/moderatorPanel";
    }

    @GetMapping("/management/support-tickets/{id}")
    public String supportAnswer (@PathVariable int id) {
        return "/admin/supportAnswer";
    }
}