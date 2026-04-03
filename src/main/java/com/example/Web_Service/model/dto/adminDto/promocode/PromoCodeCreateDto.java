package com.example.Web_Service.model.dto.adminDto.promocode;

import com.example.Web_Service.model.enums.PromoCodeStatus;
import com.example.Web_Service.model.enums.PromoCodeType;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public class PromoCodeCreateDto {
    @NotBlank(message = "Промокод не может быть пустым!")
    @Length(min = 5, max = 30, message = "Длина промокода не может быть меньше 5 символов или больше 30")
    private String promoCode;
    @NotNull(message = "Количество использований не может быть пустым!")
    @Positive(message = "Количество использований не может быть отрицательным или равным нулю")
    private Integer count;
    @Future(message = "Дата истечения промокода не может быть в прошлом!")
    private LocalDateTime expiresAt;
    @NotNull(message = "Тип промокода не может отсутствовать")
    private PromoCodeType promoCodeType;
    @NotNull(message = "Статус промокода не может отсутствовать")
    private PromoCodeStatus promoCodeStatus;
    @Positive(message = "Выдаваемое количество монет не должно быть отрицательным или равное нулю!")
    private Integer money = null;
    private boolean url = false;
    private String role = null;

    public PromoCodeCreateDto (String promoCode, Integer count, LocalDateTime expiresAt, PromoCodeType promoCodeType, PromoCodeStatus promoCodeStatus, Integer money, boolean url, String role) {
        this.promoCode = promoCode;
        this.count = count;
        this.expiresAt = expiresAt;
        this.promoCodeType = promoCodeType;
        this.promoCodeStatus = promoCodeStatus;
        this.money = money;
        this.url = url;
        this.role = role;
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

    public Integer getMoney() {
        return money;
    }

    public boolean getUrl() {
        return url;
    }

    public String getRole() {
        return role;
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

    public void setMoney(Integer money) {
        this.money = money;
    }

    public void setUrl(boolean url) {
        this.url = url;
    }

    public void setRole(String role) {
        this.role = role;
    }
}