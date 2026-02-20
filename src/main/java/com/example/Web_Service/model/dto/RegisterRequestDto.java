package com.example.Web_Service.model.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class RegisterRequestDto {
    @NotBlank(message = "Имя не может быть пустым!")
    private String name;
    @NotBlank(message = "Никнейм не может быть пустым!")
    private String username;
    @NotBlank(message = "Пароль не может быть пустым!")
    @Size(min = 12, message = "Пароль должен иметь длину больше 12 символов")
    private String password;
    @Email(message = "Почта некорректная!")
    @NotBlank(message = "Почта не может быть пустой!")
    private String email;
    @NotBlank(message = "Номер телефона не может быть пустым!")
    @Size(min = 10, max = 15)
    @Pattern(regexp = "^[+]?([0-9\\s-]{10,15})$", message = "Некорректный номер телефона, пример корректного номера телефона: 89987026755 или 8-998-702-67-55")
    private String phone;
    @PastOrPresent(message = "Дата не может быть в будущем!")
    private LocalDate birthdate;

    public RegisterRequestDto(String name, String username, String password, String email, String phone, LocalDate birthdate) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.birthdate = birthdate;
    }

    public RegisterRequestDto() {}

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBirthdate(LocalDate brithday) {
        this.birthdate = brithday;
    }
}