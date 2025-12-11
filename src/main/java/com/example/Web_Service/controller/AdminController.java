package com.example.Web_Service.controller;

import com.example.Web_Service.model.Dish;
import com.example.Web_Service.model.Employee;
import com.example.Web_Service.model.User;
import com.example.Web_Service.model.Product;
import com.example.Web_Service.repository.DishRepository;
import com.example.Web_Service.repository.EmployeeRepository;
import com.example.Web_Service.repository.ProductRepository;
import com.example.Web_Service.repository.UserRepository;
import com.example.Web_Service.users.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class AdminController {
    private final UserRepository userRepository;
    private final DishRepository dishRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;

    public AdminController(UserRepository userRepository, DishRepository dishRepository, EmployeeRepository employeeRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
        this.employeeRepository = employeeRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/admin")
    public String admin(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = customUserDetails.getUser();
            List<User> usersList = userRepository.allUsers();
            List<Dish> dishList = dishRepository.findAllDish();
            List<Employee> employeeList = employeeRepository.findAllEmployees();
            List<Product> productList = productRepository.findAllProducts();

            model.addAttribute("user", user);
            model.addAttribute("userList", usersList);
            model.addAttribute("dishList", dishList);
            model.addAttribute("employeeList", employeeList);
            model.addAttribute("productList", productList);

        }

        return "admin";
    }

    @PostMapping("/admin/update-user")
    public String updateUser (@Valid @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "updateUser";
        }

        User currentUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с " + user.getId() + " не найден!"));

        currentUser.setName(user.getName());
        currentUser.setUsername(user.getUsername());
        currentUser.setEmail(user.getEmail());
        currentUser.setPhone(user.getPhone());
        currentUser.setBirthdate(user.getBirthdate());
        currentUser.setBalance(user.getBalance());
        currentUser.setRole(user.getRole());

        userRepository.save(currentUser);
        return "redirect:/admin";
    }

    @GetMapping("/admin/update-user/{id}")
    public String showUpdateForm (@PathVariable int id, Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("Аккаунт с " + id + " не найден!"));
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            User administrator = customUserDetails.getUser();
            model.addAttribute("user", user);
            model.addAttribute("administrator", administrator);
            return "updateUser";
        }

        return "redirect:/admin";
    }

    @GetMapping("/admin/change-password/{id}")
    public String showChangePasswordForm (@PathVariable int id, Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("Аккаунт с " + id + " не был найден!"));
                CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
                User administrator = customUserDetails.getUser();

                model.addAttribute("administrator", administrator);
                model.addAttribute("user", user);
                return "changePassword";
        }

        return "redirect:/admin";
    }

    @PostMapping("/admin/change-password")
    public String updatePassword (@Valid @ModelAttribute("user") User user, BindingResult result, Model model, Authentication authentication) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User administrator = customUserDetails.getUser();

        if (result.hasErrors()) {
            model.addAttribute("administrator", administrator);
            model.addAttribute("user", user);
            model.addAttribute("error", result.getAllErrors());
            return "changePassword";
        }

        User currentUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с " + user.getId() + " не найден!"));

        currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(currentUser);
        return "redirect:/admin";
    }

    @GetMapping("/in-development")
    public String inDevelopment () {
        return "inDevelopment";
    }
}