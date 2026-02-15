package com.example.Web_Service.model.dto;

import com.example.Web_Service.jsondeserialize.StringTrimDeserializer;
import com.example.Web_Service.model.enums.Role;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class UpdateDataUserRequestDto {
    @JsonDeserialize(using = StringTrimDeserializer.class)
    private String name;
    @JsonDeserialize(using = StringTrimDeserializer.class)
    private String username;
    @JsonDeserialize(using = StringTrimDeserializer.class)
    @Email
    private String email;
    @JsonDeserialize(using = StringTrimDeserializer.class)
    @Pattern(regexp = "^[+]?([0-9\\s-]{0,15})$", message = "Некорректный номер телефона, пример корректного номера телефона: 89987026755 или 8-998-702-67-55")
    private String phone;
    private Role role;
    private int balance;

    public UpdateDataUserRequestDto(String name, String username, String email, String phone, Role role, int balance) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.balance = balance;
    }

    public UpdateDataUserRequestDto() {}

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Role getRole() {
        return role;
    }

    public int getBalance() {
        return balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
