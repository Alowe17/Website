package com.example.Web_Service.model.dto.adminDto.promocode.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

public class PromoCodeUpdateDto {
    private int id;
    private String promoCode;
    @PositiveOrZero(message = "Количество использований не может быть меньше нуля!")
    private Integer count;
    @Future(message = "Дата истечения промокода не может быть в прошлом!")
    private LocalDateTime expiresAt;
    private String promoCodeType;
    private String promoCodeStatus;

    public PromoCodeUpdateDto (int id, String promoCode, Integer count, LocalDateTime expiresAt, String promoCodeType, String promoCodeStatus) {
        this.id = id;
        this.promoCode = promoCode;
        this.count = count;
        this.expiresAt = expiresAt;
        this.promoCodeType = promoCodeType;
        this.promoCodeStatus = promoCodeStatus;
    }

    public int getId() {
        return id;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public Integer getCount() {
        return count;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public String getPromoCodeType() {
        return promoCodeType;
    }

    public String getPromoCodeStatus() {
        return promoCodeStatus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setPromoCodeType(String promoCodeType) {
        this.promoCodeType = promoCodeType;
    }

    public void setPromoCodeStatus(String promoCodeStatus) {
        this.promoCodeStatus = promoCodeStatus;
    }
}