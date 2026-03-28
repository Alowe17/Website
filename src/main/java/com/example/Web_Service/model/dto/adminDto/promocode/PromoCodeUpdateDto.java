package com.example.Web_Service.model.dto.adminDto.promocode;

import com.example.Web_Service.model.enums.PromoCodeStatus;
import com.example.Web_Service.model.enums.PromoCodeType;

import java.time.LocalDateTime;

public class PromoCodeUpdateDto {
    private String promoCode;
    private Integer count;

    private LocalDateTime expiryAt;
    private PromoCodeType promoCodeType;
    private PromoCodeStatus promoCodeStatus;

    public PromoCodeUpdateDto (String promoCode, Integer count, LocalDateTime expiryAt, PromoCodeType promoCodeType, PromoCodeStatus promoCodeStatus) {
        this.promoCode = promoCode;
        this.count = count;
        this.expiryAt = expiryAt;
        this.promoCodeType = promoCodeType;
        this.promoCodeStatus = promoCodeStatus;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public Integer getCount() {
        return count;
    }

    public LocalDateTime getExpiryAt() {
        return expiryAt;
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

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setExpiryAt(LocalDateTime expiryAt) {
        this.expiryAt = expiryAt;
    }

    public void setPromoCodeType(PromoCodeType promoCodeType) {
        this.promoCodeType = promoCodeType;
    }

    public void setPromoCodeStatus(PromoCodeStatus promoCodeStatus) {
        this.promoCodeStatus = promoCodeStatus;
    }
}