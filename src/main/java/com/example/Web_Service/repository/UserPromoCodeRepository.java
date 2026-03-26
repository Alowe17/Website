package com.example.Web_Service.repository;

import com.example.Web_Service.model.entity.PromoCode;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.entity.UserPromoCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPromoCodeRepository extends JpaRepository<UserPromoCode, Long> {
    boolean existsByUserAndPromoCode(User user, PromoCode promoCode);
}