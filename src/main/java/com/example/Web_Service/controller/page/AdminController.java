package com.example.Web_Service.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminController {
    @GetMapping("/admin")
    public String admin() {
        return "admin/admin";
    }

    @GetMapping("/admin/change-password/{username}")
    public String formChangePassword (@PathVariable String username) {
        return "admin/changePassword";
    }

    @GetMapping("/admin/update-user/{username}")
    public String updateUser (@PathVariable String username) {
        return "admin/updateUser";
    }

    @GetMapping("/in-development")
    public String inDevelopment () {
        return "inDevelopment";
    }
}