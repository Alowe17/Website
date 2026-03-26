package com.example.Web_Service.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_promo_codes")
public class UserPromoCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    private PromoCode promoCode;

    public UserPromoCode (User user, PromoCode promoCode) {
        this.user = user;
        this.promoCode = promoCode;
    }

    public UserPromoCode () {}

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public PromoCode getPromoCode() {
        return promoCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPromoCode(PromoCode promoCode) {
        this.promoCode = promoCode;
    }
}