package com.example.Web_Service.controller;

import com.example.Web_Service.model.entity.MessageSupport;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.enums.Status;
import com.example.Web_Service.repository.MessageSupportRepository;
import com.example.Web_Service.users.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class SupportController {
    private final MessageSupportRepository messageSupportRepository;

    public SupportController(MessageSupportRepository messageSupportRepository) {
        this.messageSupportRepository = messageSupportRepository;
    }

    @GetMapping("/support")
    public String support(Authentication authentication, Model model) {
        if (authentication == null) {
            return "redirect:/login";
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();
        model.addAttribute("user", user);
        return "support";
    }

    @PostMapping("/support/sent")
    public String sentMessage (@RequestParam String message, Authentication authentication, Model model) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        if (authentication == null) {
            return "redirect:/login";
        }

        message.trim();

        if (message.isEmpty()) {
            model.addAttribute("errorMessage", "Сообщение пустое!");
            return "support";
        }

        LocalDate date = LocalDate.now();

        messageSupportRepository.save(new MessageSupport(0, message, Status.NEW, date, user));
        model.addAttribute("successMessage", "Обращение успешно отправлено! Спасибо ❤\uFE0F");
        return "support";
    }
}