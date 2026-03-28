package com.example.Web_Service.repository;

import com.example.Web_Service.model.entity.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromoCodeRepository extends JpaRepository<PromoCode, String> {
    Optional<PromoCode> findByPromoCode (String promoCode);
    Optional<PromoCode> findById (int id);
}