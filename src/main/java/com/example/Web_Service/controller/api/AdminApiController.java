package com.example.Web_Service.controller.api;

import com.example.Web_Service.model.dto.*;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.service.AdminService;
import com.example.Web_Service.users.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AdminApiController {
    private final AdminService adminService;

    public AdminApiController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/api/admin")
    public ResponseEntity<?> getRights (Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        if (user.getRole().name().equals("ADMINISTRATOR")) {
            return ResponseEntity.ok().body(Map.of("message", "Добро пожаловать, " + customUserDetails.getUsername()));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "У вас недостаточно прав для посещения данной страницы!"));
    }

    @GetMapping("/api/admin-list/user")
    public ResponseEntity<?> getAdminListUser () {
        List<UserProgressDto> list = adminService.getListUserProgressDto();

        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("message", "В базе данных не найдено учетные записи пользователей!"));
        }

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/api/admin-list/employee")
    public ResponseEntity<?> getAdminListEmployee () {
        List<EmployeeDto> list = adminService.getListEmployeeDto();

        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("message", "В базе данных не найдено NPC!"));
        }

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/api/admin-list/dish")
    public ResponseEntity<?> getAdminListDish () {
        List<DishDto> list = adminService.getListDishDto();

        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "В базе данных не найдено блюд!"));
        }

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/api/admin-list/product")
    public ResponseEntity<?> getAdminListProduct () {
        List<ProductDto> list = adminService.getListProductDto();

        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("message", "В базе данных не найдено товаров!"));
        }

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/api/admin-list/support")
    public ResponseEntity<?> getAdminListSupport () {
        List<SupportMessageResponseDto> list = adminService.getListSupportMessageDto();

        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/api/admin/password/{username}")
    public ResponseEntity<?> getAdminUpdatePassword (@PathVariable String username) {
        UserUpdatePasswordRequestDto user = adminService.getUserUpdatePasswordDto(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/api/admin/change-password")
    public ResponseEntity<?> updatePassword (@Valid @RequestBody UserUpdatePasswordRequestDto userUpdatePasswordRequestDto) {
        String message = adminService.updatePasswordUserValidator(userUpdatePasswordRequestDto);

        if (message != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", message));
        }

        return ResponseEntity.ok().body(Map.of("message", "Пароль был успешно изменен!"));
    }

    @GetMapping("/api/admin/user-info/{username}")
    public ResponseEntity<?> getUserInfo (@PathVariable String username) {
        UserDto user = adminService.getUserData(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/api/admin/user-update-data/{username}")
    public ResponseEntity<?> updateUserData (@PathVariable String username, @Valid @RequestBody UpdateDataUserRequestDto updateDataUserRequestDto) {
        String message = adminService.updateDataUserValidator(updateDataUserRequestDto, username);

        if (message != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", message));
        }

        return ResponseEntity.ok().body(Map.of("message", "Данные аккаунта успешно обновлены!"));
    }
}