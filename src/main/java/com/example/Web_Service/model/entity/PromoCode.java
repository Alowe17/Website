package com.example.Web_Service.model.entity;

import com.example.Web_Service.model.enums.PromoCodeStatus;
import com.example.Web_Service.model.enums.PromoCodeType;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "promo_code")
public class PromoCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, length = 30, nullable = false)
    private String promoCode;
    private int count;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt = LocalDateTime.now();
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime expiresAt;
    @ManyToOne
    private User administrator;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PromoCodeType promoCodeType;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PromoCodeStatus promoCodeStatus;

    public PromoCode (int id, String promoCode, int count, LocalDateTime createdAt, LocalDateTime expiresAt, User administrator, PromoCodeType promoCodeType, PromoCodeStatus promoCodeStatus) {
        this.id = id;
        this.promoCode = promoCode;
        this.count = count;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.administrator = administrator;
        this.promoCodeType = promoCodeType;
        this.promoCodeStatus = promoCodeStatus;
    }

    public PromoCode () {}

    public int getId() {
        return id;
    }

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

    public void setId(int id) {
        this.id = id;
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