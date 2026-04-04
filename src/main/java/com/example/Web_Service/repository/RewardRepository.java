package com.example.Web_Service.repository;

import com.example.Web_Service.model.entity.PromoCode;
import com.example.Web_Service.model.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RewardRepository extends JpaRepository<Reward,Long> {
    List<Reward> findByPromoCode (PromoCode promoCode);
    Optional<Reward> findById (int id);
}