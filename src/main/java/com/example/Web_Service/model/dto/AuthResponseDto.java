package com.example.Web_Service.model.dto;

public class AuthResponseDto {
    private String accessToken;

    public AuthResponseDto (String accessToken) {
        this.accessToken = accessToken;
    }

    public AuthResponseDto () {}

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}