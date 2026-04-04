package com.example.Web_Service.model.dto.adminDto.reward.response;

import com.example.Web_Service.model.entity.PromoCode;
import com.example.Web_Service.model.enums.Role;

public class RewardDto {
    private int id;
    private PromoCode promoCode;
    private int balance;
    private String url;
    private Role role;

    public RewardDto (int id, PromoCode promoCode, int balance, String url, Role role) {
        this.id = id;
        this.promoCode = promoCode;
        this.balance = balance;
        this.url = url;
        this.role = role;
    }

    public RewardDto () {}

    public int getId() {
        return id;
    }

    public PromoCode getPromoCode() {
        return promoCode;
    }

    public int getBalance() {
        return balance;
    }

    public String getUrl() {
        return url;
    }

    public Role getRole() {
        return role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPromoCode(PromoCode promoCode) {
        this.promoCode = promoCode;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}