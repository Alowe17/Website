package com.example.Web_Service.model.dto.adminDto.reward.request;

public class RewardUpdateDto {
    private int id;
    private Integer balance;
    private String url;
    private String role;

    public RewardUpdateDto (int id, Integer balance, String url, String role) {
        this.id = id;
        this.balance = balance;
        this.url = url;
        this.role = role;
    }

    public RewardUpdateDto() {}

    public int getId() {
        return id;
    }

    public Integer getBalance() {
        return balance;
    }

    public String getUrl() {
        return url;
    }

    public String getRole() {
        return role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setRole(String role) {
        this.role = role;
    }
}