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

    @GetMapping("/admin/password/{username}")
    public String formChangePassword (@PathVariable String username) {
        return "admin/changePassword";
    }

    @GetMapping("/admin/users/{username}")
    public String updateUser (@PathVariable String username) {
        return "admin/updateUser";
    }

    @GetMapping("/in-development")
    public String inDevelopment () {
        return "inDevelopment";
    }

    @GetMapping("/admin/npcs")
    public String createNewNPC () {
        return "admin/createNewNPC";
    }

    @GetMapping("/admin/npcs/{username}")
    public String npcInfo (@PathVariable String username) {
        return "admin/updateNpc";
    }

    @GetMapping("/admin/dishes")
    public String createNewDish () {
        return "admin/createNewDish";
    }

    @GetMapping("/admin/dishes/{id}")
    public String dishInfo (@PathVariable int id) {
        return "/admin/updateDish";
    }

    @GetMapping("/admin/products")
    public String createNewProduct () {
        return "admin/createNewProduct";
    }

    @GetMapping("/admin/products/{id}")
    public String productInfo (@PathVariable int id) {
        return "/admin/updateProduct";
    }

    @GetMapping("/admin/promo-codes")
    public String createNewPromoCode () {
        return "admin/createNewPromoCode";
    }
}