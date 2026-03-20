package com.example.Web_Service.model.entity;

import com.example.Web_Service.model.enums.Role;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "reward")
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private PromoCode promoCode;
    private int balance;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(length = 60)
    private String url = UUID.randomUUID().toString();

    public Reward (int id, PromoCode promoCode, int balance, Role role, String url) {
        this.id = id;
        this.promoCode = promoCode;
        this.balance = balance;
        this.role = role;
        this.url = url;
    }

    public Reward () {}

    public int getId() {
        return id;
    }

    public PromoCode getPromoCode() {
        return promoCode;
    }

    public int getBalance() {
        return balance;
    }

    public Role getRole() {
        return role;
    }

    public String getUrl() {
        return url;
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

    public void setRole(Role role) {
        this.role = role;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}