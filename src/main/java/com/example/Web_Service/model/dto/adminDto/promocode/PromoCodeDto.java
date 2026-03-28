package com.example.Web_Service.model.dto.adminDto.promocode;

import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.enums.PromoCodeStatus;
import com.example.Web_Service.model.enums.PromoCodeType;

import java.time.LocalDateTime;

public class PromoCodeDto {
    private String promoCode;
    private int count;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private User administrator;
    private PromoCodeType promoCodeType;
    private PromoCodeStatus promoCodeStatus;

    public PromoCodeDto (String promoCode, int count, LocalDateTime createdAt, LocalDateTime expiresAt, User administrator, PromoCodeType promoCodeType, PromoCodeStatus promoCodeStatus) {
        this.promoCode = promoCode;
        this.count = count;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.administrator = administrator;
        this.promoCodeType = promoCodeType;
        this.promoCodeStatus = promoCodeStatus;
    }

    public PromoCodeDto () {}

    public String getPromoCode() {
        return promoCode;
    }

    public int getCount() {
        return count;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public User getAdministrator() {
        return administrator;
    }

    public PromoCodeType getPromoCodeType() {
        return promoCodeType;
    }

    public PromoCodeStatus getPromoCodeStatus() {
        return promoCodeStatus;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setAdministrator(User administrator) {
        this.administrator = administrator;
    }

    public void setPromoCodeType(PromoCodeType promoCodeType) {
        this.promoCodeType = promoCodeType;
    }

    public void setPromoCodeStatus(PromoCodeStatus promoCodeStatus) {
        this.promoCodeStatus = promoCodeStatus;
    }
}