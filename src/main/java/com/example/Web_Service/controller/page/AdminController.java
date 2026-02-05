package com.example.Web_Service.controller.page;

import com.example.Web_Service.model.entity.*;
import com.example.Web_Service.repository.*;
import com.example.Web_Service.users.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class AdminController {
    private final UserRepository userRepository;
    private final DishRepository dishRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;
    private final MessageSupportRepository messageSupportRepository;

    public AdminController(UserRepository userRepository, DishRepository dishRepository, EmployeeRepository employeeRepository, ProductRepository productRepository, MessageSupportRepository messageSupportRepository) {
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
        this.employeeRepository = employeeRepository;
        this.productRepository = productRepository;
        this.messageSupportRepository = messageSupportRepository;
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
            List<MessageSupport> messageSupportList = messageSupportRepository.findAllMessageSupport();

            model.addAttribute("user", user);
            model.addAttribute("userList", usersList);
            model.addAttribute("dishList", dishList);
            model.addAttribute("employeeList", employeeList);
            model.addAttribute("productList", productList);
            model.addAttribute("messageSupportList", messageSupportList);
        }

        return "admin/admin";
    }

    @PostMapping("/admin/update-user")
    public String updateUser (@Valid @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/updateUser";
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
        currentUser.setProgress_level(user.getProgress_level());
        currentUser.setProgress_xp(user.getProgress_xp());

        userRepository.save(currentUser);
        return "redirect:/admin/admin";
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
            return "admin/updateUser";
        }

        return "redirect:/admin/admin";
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
                return "admin/changePassword";
        }

        return "redirect:/admin/admin";
    }

    @PostMapping("/admin/change-password")
    public String updatePassword (@Valid @ModelAttribute("user") User user, BindingResult result, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User administrator = customUserDetails.getUser();

        if (result.hasErrors()) {
            model.addAttribute("administrator", administrator);
            model.addAttribute("user", user);
            model.addAttribute("errorMessage", result.getAllErrors());
            return "admin/changePassword";
        }

        User currentUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с " + user.getId() + " не найден!"));

        currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(currentUser);
        redirectAttributes.addFlashAttribute("successMessage", "Пароль успешно изменен!");
        return "admin/changePassword";
    }

    @GetMapping("/in-development")
    public String inDevelopment () {
        return "inDevelopment";
    }
}