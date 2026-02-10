package com.example.Web_Service.model.dto;

import com.example.Web_Service.jsondeserialize.StringTrimDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UpdateUserRequestDto {
    @JsonDeserialize(using = StringTrimDeserializer.class)
    private String name;
    @JsonDeserialize(using = StringTrimDeserializer.class)
    private String username;
    @JsonDeserialize(using = StringTrimDeserializer.class)
    private String password;
    @Email(message = "Некорректный формат почты")
    @JsonDeserialize(using = StringTrimDeserializer.class)
    private String email;
    @JsonDeserialize(using = StringTrimDeserializer.class)
    @Pattern(regexp = "^[+]?([0-9\\s-]{0,15})$", message = "Некорректный номер телефона, пример корректного номера телефона: 89987026755 или 8-998-702-67-55")
    private String phone;

    public UpdateUserRequestDto (String name, String username, String password, String email, String phone) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public UpdateUserRequestDto () {}

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
}