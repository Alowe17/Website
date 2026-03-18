package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.promo.request.PromoCodeDto;
import com.example.Web_Service.model.entity.PromoCode;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.enums.PromoCodeStatus;
import com.example.Web_Service.model.enums.PromoCodeType;
import com.example.Web_Service.repository.PromoCodeRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class PromoCodeService {
    private final PromoCodeRepository promoCodeRepository;
    private final RewardService rewardService;

    public PromoCodeService (PromoCodeRepository promoCodeRepository, RewardService rewardService) {
        this.promoCodeRepository = promoCodeRepository;
        this.rewardService = rewardService;
    }

    public String validate (String code) {
        PromoCode promoCode = promoCodeRepository.findByPromoCode(code).orElse(null);

        if (promoCode == null) {
            return "Не удалось найти промокод!";
        }

        if (promoCode.getPromoCodeStatus() != PromoCodeStatus.AVAILABLE) {
            return "Промокод недоступен или не существует!";
        }

        if (promoCode.getExpiresAt() == null || promoCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            return "Срок действия промокода истек!";
        }

        if (promoCode.getCount() <= 0) {
            return "Промокод больше нельзя использовать!";
        }

        return null;
    }

    public String validateCreateData (PromoCode createPromoCode) {
        if (promoCodeRepository.findByPromoCode(createPromoCode.getPromoCode()).isPresent()) {
            return "Такой промокод уже существует!";
        }

        if (createPromoCode.getPromoCode() == null || createPromoCode.getPromoCode().isBlank()) {
            return "Промокод не может быть пустым!";
        }

        if (createPromoCode.getExpiresAt() == null) {
            return "Промокод не может иметь дату истечения null!";
        }

        if (createPromoCode.getCreatedAt() != null &&
                createPromoCode.getExpiresAt() != null &&
                createPromoCode.getCreatedAt().isAfter(createPromoCode.getExpiresAt())) {
            return "Дата создания не может быть после даты истечения!";
        }

        if (createPromoCode.getCount() <= 0) {
            return "Количество использований должно быть больше нуля!";
        }

        if (createPromoCode.getPromoCodeType() == null) {
            return "Промокод не может иметь тип null!";
        }

        if (createPromoCode.getPromoCodeStatus() == null) {
            return "Промокод не может иметь статус null!";
        }

        return null;
    }

    public ResponseEntity<?> updateAdmin (PromoCode updatePromoCode, PromoCode currentPromoCode) {
        boolean changes = isModified (updatePromoCode, currentPromoCode);

        if (!changes) {
            return ResponseEntity.badRequest().body(Map.of("message", "Не были внесены изменения!"));
        }

        promoCodeRepository.save(currentPromoCode);
        return ResponseEntity.ok().body(Map.of("message", "Изменения успешно внесены!"));
    }

    public ResponseEntity<?> update (PromoCode promoCode, User user) {
        if (promoCode.getCount() <= 0) {
            return ResponseEntity.status(400).body(Map.of("message", "Невозможно использовать этот промокод!"));
        }

        promoCode.setCount(promoCode.getCount() - 1);
        promoCodeRepository.save(promoCode);
        return rewardService.giveRewardUser(user, promoCode);
    }

    public boolean isModified (PromoCode updatePromoCode, PromoCode currentPromoCode) {
        boolean flag = false;

        if (updatePromoCode.getPromoCode() != null && !updatePromoCode.getPromoCode().isBlank() &&  !updatePromoCode.getPromoCode().equals(currentPromoCode.getPromoCode())) {
            currentPromoCode.setPromoCode(updatePromoCode.getPromoCode());
            flag = true;
        }

        if (updatePromoCode.getCount() > 0 && updatePromoCode.getCount() != currentPromoCode.getCount()) {
            currentPromoCode.setCount(updatePromoCode.getCount());
            flag = true;
        }

        if (updatePromoCode.getCreatedAt() != null && updatePromoCode.getCreatedAt().isBefore(currentPromoCode.getExpiresAt()) && !updatePromoCode.getCreatedAt().equals(currentPromoCode.getCreatedAt())) {
            currentPromoCode.setCreatedAt(updatePromoCode.getCreatedAt());
            flag = true;
        }

        if (updatePromoCode.getExpiresAt() != null && !updatePromoCode.getExpiresAt().equals(currentPromoCode.getExpiresAt())) {
            currentPromoCode.setExpiresAt(updatePromoCode.getExpiresAt());
            flag = true;
        }

        if (updatePromoCode.getPromoCodeType() != null && updatePromoCode.getPromoCodeType() != currentPromoCode.getPromoCodeType()) {
            currentPromoCode.setPromoCodeType(updatePromoCode.getPromoCodeType());
            flag = true;
        }

        if (updatePromoCode.getPromoCodeStatus() != null && updatePromoCode.getPromoCodeStatus() != currentPromoCode.getPromoCodeStatus()) {
            currentPromoCode.setPromoCodeStatus(updatePromoCode.getPromoCodeStatus());
            flag = true;
        }

        return flag;
    }

    @Transactional
    public ResponseEntity<?> usePromoCode (PromoCodeDto promoCodeDto, User user) {
        String message = validate(promoCodeDto.getPromoCode());

        if (message != null) {
            return ResponseEntity.badRequest().body(Map.of("message", message));
        }

        PromoCode promoCode = promoCodeRepository.findByPromoCode(promoCodeDto.getPromoCode()).orElse(null);

        if (promoCode == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Не удалось найти промокод!"));
        }

        return update(promoCode, user);
    }

    public PromoCode getPromoCode (PromoCodeDto promoCodeDto, User user) {
        return promoCodeRepository.findByPromoCode(promoCodeDto.getPromoCode()).orElse(null);
    }
}