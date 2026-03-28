package com.example.Web_Service.model.dto.promo.request;

public class PromoCodeUseDto {
    private String promoCode;

    public PromoCodeUseDto () {}

    public PromoCodeUseDto (String promoCode) {
        this.promoCode = promoCode;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }
}