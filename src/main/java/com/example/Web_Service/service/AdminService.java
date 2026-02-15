package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.*;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.enums.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final UserProgressService userProgressService;
    private final DishService dishService;
    private final EmployeeService employeeService;
    private final ProductService productService;
    private final SupportService supportService;
    private final UserService userService;

    public AdminService(UserProgressService userProgressService, DishService dishService, EmployeeService employeeService, ProductService productService, SupportService supportService, UserService userService) {
        this.userProgressService = userProgressService;
        this.dishService = dishService;
        this.employeeService = employeeService;
        this.productService = productService;
        this.supportService = supportService;
        this.userService = userService;
    }

    public List<UserProgressDto> getListUserProgressDto () {
        List<UserProgressDto> list = userProgressService.getListUserProgressDto();

        if (list.isEmpty()) {
            return List.of();
        }

        return list;
    }

    public List<EmployeeDto> getListEmployeeDto () {
        List<EmployeeDto> list = employeeService.getListEmployeeDto();

        if (list.isEmpty()) {
            return List.of();
        }

        return list;
    }

    public List<DishDto> getListDishDto () {
        List<DishDto> list = dishService.getListDishDto();

        if (list.isEmpty()) {
            return List.of();
        }

        return list;
    }

    public List<ProductDto> getListProductDto () {
        List<ProductDto> list = productService.getListProductDto();

        if (list.isEmpty()) {
            return List.of();
        }

        return list;
    }

    public List<SupportMessageResponseDto> getListSupportMessageDto () {
        List<SupportMessageResponseDto> list = supportService.getListMessageSupportDto();

        if (list.isEmpty()) {
            return List.of();
        }

        return list;
    }

    public UserUpdatePasswordRequestDto getUserUpdatePasswordDto (String username) {
        User user = userService.getUserUsername(username);

        if (user == null) {
            return null;
        }

        return new UserUpdatePasswordRequestDto(
                user.getUsername(),
                user.getPassword()
        );
    }

    public String updatePasswordUserValidator (UserUpdatePasswordRequestDto userUpdatePasswordRequestDto) {
        User user = userService.getUserUsername(userUpdatePasswordRequestDto.getUsername());

        if (user == null) {
            return "Пользователь " + userUpdatePasswordRequestDto.getUsername() + " не найден!";
        }

        String password = userUpdatePasswordRequestDto.getPassword().trim();

        if (password.isBlank()) {
            return "Нельзя изменить пароль на пустой или состоящий из пробелов!";
        }

        if (password.length() < 12) {
            return "Запрещено ставить пароли длина которых меньше 12 символов!";
        }

        if (!userService.updateUserPassword(userUpdatePasswordRequestDto.getUsername(), userUpdatePasswordRequestDto.getPassword())) {
            return "Не удалось обновить пароль пользователю: " + userUpdatePasswordRequestDto.getUsername() + "!";
        }

        return null;
    }

    public UserDto getUserData (String username) {
        User user = userService.getUserUsername(username);

        if (user == null) {
            return null;
        }

        return new UserDto(
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getBirthdate(),
                user.getRole(),
                user.getBalance()
        );
    }

    public String updateDataUserValidator (UpdateDataUserRequestDto updateDataUserRequestDto, String oldUsername) {
        String message = userService.userDataValidator(updateDataUserRequestDto);

        if (message != null) {
            return message;
        }

        if (updateDataUserRequestDto.getBalance() < 0) {
            return "Баланс не может быть отрицательным!";
        }

        String name = updateDataUserRequestDto.getName().trim();
        String username = updateDataUserRequestDto.getUsername().trim();
        String email = updateDataUserRequestDto.getEmail().trim();
        String phone = updateDataUserRequestDto.getPhone().trim();
        Role role = updateDataUserRequestDto.getRole();

        if (name.isBlank()) {
            updateDataUserRequestDto.setName(null);
        }

        if (username.isBlank()) {
            updateDataUserRequestDto.setUsername(null);
        }

        if (email.isBlank()) {
            updateDataUserRequestDto.setEmail(null);
        }

        if (phone.isBlank()) {
            updateDataUserRequestDto.setPhone(null);
        }

        User user = userService.getUserUsername(oldUsername);

        if (user == null) {
            return "Не удалось найти игрока по никнейму: " + oldUsername + "!";
        }

        return userService.updateUserData(updateDataUserRequestDto, user);
    }
}