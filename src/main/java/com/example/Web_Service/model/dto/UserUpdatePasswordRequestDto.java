package com.example.Web_Service.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserUpdatePasswordRequestDto {
    private String username;
    @NotBlank(message = "Пароль не может быть пустым!")
    @Size(min = 12, message = "Пароль должен быть больше 12 символов")
    private String password;

    public UserUpdatePasswordRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserUpdatePasswordRequestDto() {}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}