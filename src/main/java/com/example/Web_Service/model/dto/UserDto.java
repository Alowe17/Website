package com.example.Web_Service.model.dto;

import com.example.Web_Service.model.enums.Role;

import java.time.LocalDate;

public class UserDto {
    private String name;
    private String username;
    private String password;
    private String email;
    private String phone;
    private LocalDate birthdate;
    private Role role;
    private int balance;

    public UserDto (String name, String username, String password, String email, String phone, LocalDate birthdate, Role role, int balance) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.birthdate = birthdate;
        this.role = role;
        this.balance = balance;
    }

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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}