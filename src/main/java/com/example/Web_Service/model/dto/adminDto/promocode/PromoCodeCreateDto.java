package com.example.Web_Service.model.dto.adminDto.promocode;

import com.example.Web_Service.model.enums.PromoCodeStatus;
import com.example.Web_Service.model.enums.PromoCodeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public class PromoCodeCreateDto {
    @NotBlank(message = "Промокод не может быть пустым!")
    @Length(min = 5, max = 30, message = "Длина промокода не может быть меньше 5 символов или больше 30")
    private String promoCode;
    @Positive(message = "Количество использований не может быть отрицательным или равным нулю")
    private Integer count;
    private LocalDateTime expiresAt;
    @NotBlank(message = "Тип промокода не может отсутствовать")
    private PromoCodeType promoCodeType;
    @NotBlank(message = "Статус промокода не может отсутствовать")
    private PromoCodeStatus promoCodeStatus;

    public PromoCodeCreateDto (String promoCode, Integer count, LocalDateTime expiresAt, PromoCodeType promoCodeType, PromoCodeStatus promoCodeStatus) {
        this.promoCode = promoCode;
        this.count = count;
        this.expiresAt = expiresAt;
        this.promoCodeType = promoCodeType;
        this.promoCodeStatus = promoCodeStatus;
    }

    public PromoCodeCreateDto () {}

    public String getPromoCode() {
        return promoCode;
    }

    public Integer getCount() {
        return count;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
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

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setPromoCodeType(PromoCodeType promoCodeType) {
        this.promoCodeType = promoCodeType;
    }

    public void setPromoCodeStatus(PromoCodeStatus promoCodeStatus) {
        this.promoCodeStatus = promoCodeStatus;
    }
}