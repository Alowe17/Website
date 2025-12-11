package com.example.Web_Service.controller;

import com.example.Web_Service.model.User;
import com.example.Web_Service.model.enums.Role;
import com.example.Web_Service.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
public class RegisterController {
    private final UserRepository userRepository;

    public  RegisterController (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public String register (Authentication authentication, Model model) {
        if (authentication != null) {
            return "redirect:/index";
        }

        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register-account")
    public String registerAccount (@Valid @ModelAttribute("user") User currentUser, BindingResult result, Model model) {
        String name = currentUser.getName().trim();
        String email = currentUser.getEmail().trim();
        String password = currentUser.getPassword().trim();
        String username = currentUser.getUsername().trim();
        LocalDate data = currentUser.getBirthdate();
        String phone = currentUser.getPhone().trim();

        if (result.hasErrors()) {
            model.addAttribute("error", result.getAllErrors());
            return "register";
        }

        if (password.isBlank()) {
            model.addAttribute("error", "Пароль пустой!");
            return "register";
        }

        if (username.isBlank()) {
            model.addAttribute("error", "Никнейм пустой!");
            return "register";
        }

        if (email.isBlank()) {
            model.addAttribute("error", "Почта пустая!");
            return "register";
        }

        if (phone.isBlank()) {
            model.addAttribute("error", "Номер телефона пустой!");
            return "register";
        }

        if (name.isBlank()) {
            model.addAttribute("error", "Имя пустое!");
            return "register";
        }

        if (username.length() > 20) {
            model.addAttribute("error", "Ник слишком длинный!");
            return "register";
        }

        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Аккаунт с таким никнеймом уже существует!");
            return "register";
        }

        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "Аккаунт с такой почтой уже существует!");
            return "register";
        }

        if (userRepository.findByPhone(phone).isPresent()) {
            model.addAttribute("error", "Аккаунт с таким номером телефона уже существует!");
            return "register";
        }

        User user = new User (0, name, username, password, email, phone, data, Role.USER, 100, 1);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String passwordLocal = bCryptPasswordEncoder.encode(password);
        user.setPassword(passwordLocal);

        model.addAttribute("info", "Регистрация аккаунта прошла успешно!");
        userRepository.save(user);
        return "register";
    }
}
