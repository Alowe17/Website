package com.example.Web_Service.model.dto.promo.request;

public class PromoCodeDto {
    private String promoCode;

    public PromoCodeDto () {}

    public PromoCodeDto (String promoCode) {
        this.promoCode = promoCode;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }
}