package com.example.Web_Service.controller.api;

import com.example.Web_Service.model.dto.adminDto.*;
import com.example.Web_Service.model.dto.*;
import com.example.Web_Service.model.dto.adminDto.DishDto;
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
        List<CreateNewEmployeeRequestDto.EmployeeDto> list = adminService.getListEmployeeDto();

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

    @GetMapping("/api/admin/info-user/{username}")
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

        return ResponseEntity.ok().body(Map.of("message", "Данные аккаунта были успешно обновлены!"));
    }

    @PostMapping("/api/admin/create-new/npc")
    public ResponseEntity<?> createNewNpc (@Valid @RequestBody CreateNewEmployeeRequestDto createNewEmployeeRequestDto) {
        String message = adminService.createNewNpcValidator(createNewEmployeeRequestDto);

        if (message != null) {
            return ResponseEntity.badRequest().body(Map.of("message", message));
        }

        return ResponseEntity.ok().body(Map.of("message", "Успешно создан новый NPC!"));
    }

    @GetMapping("/api/admin/info-npc/{username}")
    public ResponseEntity<?> getNpcInfo (@PathVariable String username) {
        EmployeeUpdateResponseDto employeeUpdateResponseDto = adminService.getEmployeeDto(username);


        if (employeeUpdateResponseDto == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Не удалось найти NPC по никнейму: " + username + "!"));
        }

        return ResponseEntity.ok().body(employeeUpdateResponseDto);
    }

    @PostMapping("/api/admin/update-npc/{username}")
    public ResponseEntity<?> updateNpc (@PathVariable String username, @Valid @RequestBody EmployeeUpdateDataRequestDto employeeUpdateDataRequestDto) {
        String message = adminService.updateDataEmployeeValidator(employeeUpdateDataRequestDto, username);

        if (message != null) {
            return ResponseEntity.badRequest().body(Map.of("message", message));
        }

        return ResponseEntity.ok().body(Map.of("message", "Данные NPC были успешно обновлены!"));
    }

    @PostMapping("/api/admin/create-new/dish")
    public ResponseEntity<?> createNewDish (@Valid @RequestBody CreateNewDishDto createNewDishDto) {
        String message = adminService.createNewDishValidator (createNewDishDto);

        if (message != null) {
            return ResponseEntity.badRequest().body(Map.of("message", message));
        }

        return ResponseEntity.ok().body(Map.of("message", "Новое блюдо было успешно создано!"));
    }

    @GetMapping("/api/admin/info-dish/{dishId}")
    public ResponseEntity<?> getInfoDish (@PathVariable int dishId) {
        DishDto dishDto = adminService.getDishDto(dishId);

        if (dishDto == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Блюдо с таким ID не найдено!"));
        }

        return ResponseEntity.ok().body(dishDto);
    }

    @PostMapping("/api/admin/update-dish/{dishId}")
    public ResponseEntity<?> updateDish (@PathVariable int dishId, @Valid @RequestBody DishDto dishDto) {
        String message = adminService.updateDataDish(dishId, dishDto);

        if (message != null) {
            return ResponseEntity.badRequest().body(Map.of("message", message));
        }

        return ResponseEntity.ok().body(Map.of("message", "Данные блюда были успешно обновлены!"));
    }

    @PostMapping("/api/admin/create-new/product")
    public ResponseEntity<?> createNewProduct (@Valid @RequestBody ProductDto productDto) {
        String message = adminService.createNewProductValidator (productDto);

        if (message != null) {
            return ResponseEntity.badRequest().body(Map.of("message", message));
        }

        return ResponseEntity.ok().body(Map.of("message", "Товар успешно зарегистрирован!"));
    }

    @GetMapping("/api/admin/info-product/{id}")
    public ResponseEntity<?> getProductInfo (@PathVariable int id) {
        ProductDto productDto = adminService.getProductDto(id);

        if (productDto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(productDto);
    }

    @PostMapping("/api/admin/update-product/{id}")
    public ResponseEntity <?> updateProduct (@PathVariable int id, @Valid @RequestBody ProductDto productDto) {
        String message = adminService.updateDataProduct (id, productDto);

        if (message != null) {
            return ResponseEntity.badRequest().body(Map.of("message", message));
        }

        return ResponseEntity.ok().body(Map.of("message", "Данные товара были успешно обновлены!"));
    }

    @GetMapping("/api/admin/load-message/{id}")
    public ResponseEntity<?> getSupportMessage (@PathVariable int id) {
        SupportReplyResponseDto replyResponseDto = adminService.getSupportRetlyDto(id);

        if (replyResponseDto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(replyResponseDto);
    }

    @PostMapping("/api/admin/reply-message/{id}")
    public ResponseEntity<?> replyMessage (@PathVariable int id, @Valid @RequestBody SupportReplyRequestDto supportReplyRequestDto) {
        String message = adminService.replyToMessage(id, supportReplyRequestDto);

        System.out.println("message: " + message);

        if (message != null) {
            return ResponseEntity.badRequest().body(Map.of("message", message));
        }

        System.out.println("message: Успех");

        return ResponseEntity.ok().body(Map.of("message", "Был дан успешный ответ на обращение!"));
    }

    @PostMapping("/api/admin/rejected-message/{id}")
    public ResponseEntity<?> rejectedMessage (@PathVariable int id) {
        String message = adminService.rejectedMessage(id);

        if (message != null) {
            return ResponseEntity.badRequest().body(Map.of("message", message));
        }

        return ResponseEntity.ok().body(Map.of("message", "Обращение было успешно отклонено!"));
    }
}