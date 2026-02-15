package com.example.Web_Service.model.dto;

import com.example.Web_Service.model.enums.RoleEmployee;

public class EmployeeDto {
    private String name;
    private String username;
    private String email;
    private String phone;
    private RoleEmployee role;
    private int salary;
    private int bonus;

    public EmployeeDto(String name, String username, String email, String phone, RoleEmployee role, int salary, int bonus) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.salary = salary;
        this.bonus = bonus;
    }

    public EmployeeDto() {}

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

    public RoleEmployee getRole() {
        return role;
    }

    public int getSalary() {
        return salary;
    }

    public int getBonus() {
        return bonus;
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

    public void setRole(RoleEmployee role) {
        this.role = role;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
}