package com.example.Web_Service.model.dto.adminDto;

import com.example.Web_Service.model.enums.RoleEmployee;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

public class EmployeeUpdateDataRequestDto {
    private String name;
    private String username;
    private String password;
    @Email(message = "Некорректный формат почты!")
    private String email;
    @Pattern(regexp = "^[+]?([0-9\\s-]{0,15})$", message = "Некорректный номер телефона, пример корректного номера телефона: 89987026755 или 8-998-702-67-55")
    private String phone;
    private RoleEmployee role;
    @PositiveOrZero(message = "Значение зарплаты не может быть отрицательным!")
    private Integer salary;
    @PositiveOrZero(message = "Значение бонуса не может быть отрицательным!")
    private Integer bonus;

    public EmployeeUpdateDataRequestDto (String name, String username, String password, String email, String phone, RoleEmployee role, Integer salary, Integer bonus) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.salary = salary;
        this.bonus = bonus;
    }

    public EmployeeUpdateDataRequestDto () {}

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

    public Integer getSalary() {
        return salary;
    }

    public Integer getBonus() {
        return bonus;
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

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }
}