package com.example.Web_Service.model;

import com.example.Web_Service.model.enums.Role;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column (name = "name",  nullable = false)
    private String name;
    @Column (name = "username", nullable = false, unique = true)
    private String username;
    @Column (name = "password", nullable = false)
    private String password;
    @Column (name = "email",  nullable = false, unique = true)
    private String email;
    @Column (name = "phone", nullable = false, unique = true)
    private String phone;
    @Column(name = "birthdate", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthdate;
    @Enumerated(EnumType.STRING)
    @Column (name = "role", nullable = false)
    private Role role = Role.USER;
    @Column (name = "balance", nullable = false)
    private int balance = 100;
    @Column(name = "progress_level", nullable = false)
    private int progress_level = 0;
    @Column(name = "progress_xp", nullable = false)
    private int progress_xp = 0;

    public User (int id, String name, String username, String password, String email, String phone, LocalDate birthdate, Role role, int balance, int progress_level, int progress_xp) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.birthdate = birthdate;
        this.role = role;
        this.balance = balance;
        this.progress_level = progress_level;
        this.progress_xp = progress_xp;
    }

    public User () {}

    public void setId(int id) {
        this.id = id;
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

    public void setProgress_level(int progress_level) {
        this.progress_level = progress_level;
    }

    public void setProgress_xp(int progress_xp) {
        this.progress_xp = progress_xp;
    }

    public int getId() {
        return id;
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

    public int getProgress_level() {
        return progress_level;
    }

    public int getProgress_xp() {
        return progress_xp;
    }

    @Override
    public String toString() {
        return
                "Информация о игровом аккаунте: " + "\n" +
                        "id: " + id + "\n" +
                        "Имя: " + name + "\n" +
                        "Имя пользователя: " + username + "\n" +
                        "Пароль: " + "*".repeat(password.length()) + "\n" +
                        "Почта: " + email + "\n" +
                        "Номер телефона: " + phone + "\n" +
                        "Дата рождения: " + birthdate + "\n" +
                        "Роль: " + role + "\n" +
                        "Баланс: " + balance + "\n" +
                        "Уровень прохождения: " + progress_level + "\n" +
                        "Опыт прохождения: " + progress_xp;
    }
}
