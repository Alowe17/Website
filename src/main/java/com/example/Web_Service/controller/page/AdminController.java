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

    @GetMapping("/admin/create-new/npc")
    public String createNewNPC () {
        return "admin/createNewNPC";
    }

    @GetMapping("/admin/update-npc/{username}")
    public String npcInfo (@PathVariable String username) {
        return "admin/updateNpc";
    }

    @GetMapping("/admin/create-new/dish")
    public String createNewDish () {
        return "admin/createNewDish";
    }

    @GetMapping("/admin/update-dish/{id}")
    public String dishInfo (@PathVariable int id) {
        return "/admin/updateDish";
    }

    @GetMapping("/admin/create-new/product")
    public String createNewProduct () {
        return "admin/createNewProduct";
    }

    @GetMapping("/admin/update-product/{id}")
    public String productInfo (@PathVariable int id) {
        return "/admin/updateProduct";
    }
}