package com.example.Web_Service.model.entity;

import com.example.Web_Service.model.enums.RoleEmployee;
import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", nullable = false, length = 20)
    private String name;
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "phone", length = 20, unique = true, nullable = false)
    private String phone;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleEmployee role;
    @Column(name = "salary", nullable = false)
    private int salary;
    @Column(name = "bonus", nullable = false)
    private int bonus;

    public Employee (int id, String  name, String username, String email, String phone, RoleEmployee role, int salary, int bonus) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.salary = salary;
        this.bonus = bonus;
    }

    public Employee() {}

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

    public void setRole(RoleEmployee role) {
        this.role = role;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
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

    public RoleEmployee getRole() {
        return role;
    }

    public int getSalary() {
        return salary;
    }

    public int getBonus() {
        return bonus;
    }

    @Override
    public String toString() {
        return "Информация о сотруднике: " + "\n" +
                "id: " + id + "\n" +
                "Имя: " + name + "\n" +
                "Никнейм: " + username + "\n" +
                "Пароль: " + password + "\n" +
                "Почта: " + email + "\n" +
                "Номер телефона: " + phone + "\n" +
                "Роль: " + role + "\n" +
                "Зарплата: " + salary + "\n" +
                "Бонус: " + bonus;
    }
}
