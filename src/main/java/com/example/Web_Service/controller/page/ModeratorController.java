package com.example.Web_Service.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/moderator")
public class ModeratorController {
    @GetMapping
    public String ModeratorPanel () {
        return "moderator/moderatorPanel";
    }

    @GetMapping("/support-tickets/{id}")
    public String supportAnswer (@PathVariable int id) {
        return "/admin/supportAnswer";
    }
}