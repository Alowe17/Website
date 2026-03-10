package com.example.Web_Service.model.dto.game.user.response;

import com.example.Web_Service.model.enums.Role;

public class UserGameDto {
    private String name;
    private String username;
    private int balance;
    private Role role;

    public UserGameDto (String name, String username, int balance, Role role) {
        this.name = name;
        this.username = username;
        this.balance = balance;
        this.role = role;
    }

    public UserGameDto () {}

    public String getName () {
        return name;
    }

    public String getUsername () {
        return username;
    }

    public int getBalance () {
        return balance;
    }

    public Role getRole () {
        return role;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public void setBalance (int balance) {
        this.balance = balance;
    }

    public void setRole (Role role) {
        this.role = role;
    }
}