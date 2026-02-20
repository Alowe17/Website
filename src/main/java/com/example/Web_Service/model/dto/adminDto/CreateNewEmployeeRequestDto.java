package com.example.Web_Service.model.dto.adminDto;

import com.example.Web_Service.model.enums.RoleEmployee;
import jakarta.validation.constraints.*;

public class CreateNewEmployeeRequestDto {
    @NotBlank(message = "Имя не может быть пустым!")
    private String name;
    @NotBlank(message = "Никнейм не может быть пустым!")
    private String username;
    @NotBlank(message = "Пароль не может быть пустым!")
    @Size(min = 8, message = "Пароль не может иметь длину меньше 8 символов!")
    private String password;
    @NotBlank(message = "Почта не может быть пустой!")
    @Email(message = "Неверный формат почты!")
    private String email;
    @NotBlank(message = "Номер телефона не может быть пустым!")
    @Pattern(regexp = "^[+]?([0-9\\s-]{10,15})$", message = "Некорректный номер телефона, пример корректного номера телефона: 89987026755 или 8-998-702-67-55")
    private String phone;
    private RoleEmployee role;
    @PositiveOrZero(message = "Зарплата не может быть отрицательной!")
    private int salary;
    @PositiveOrZero(message = "Бонус не может быть отрицательным!")
    private int bonus;

    public CreateNewEmployeeRequestDto(String name, String username, String password, String email, String phone, RoleEmployee role, int salary, int bonus) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.salary = salary;
        this.bonus = bonus;
    }

    public CreateNewEmployeeRequestDto() {}

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

    public static class EmployeeDto {
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
}