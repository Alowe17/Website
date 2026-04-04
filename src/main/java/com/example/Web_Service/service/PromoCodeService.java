package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.adminDto.promocode.request.PromoCodeCreateDto;
import com.example.Web_Service.model.dto.adminDto.promocode.response.PromoCodeDto;
import com.example.Web_Service.model.dto.adminDto.promocode.request.PromoCodeUpdateDto;
import com.example.Web_Service.model.dto.promo.PromoCodeUseDto;
import com.example.Web_Service.model.entity.PromoCode;
import com.example.Web_Service.model.entity.Reward;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.entity.UserPromoCode;
import com.example.Web_Service.model.enums.PromoCodeStatus;
import com.example.Web_Service.model.enums.PromoCodeType;
import com.example.Web_Service.model.enums.Role;
import com.example.Web_Service.repository.PromoCodeRepository;
import com.example.Web_Service.repository.RewardRepository;
import com.example.Web_Service.repository.UserPromoCodeRepository;
import com.example.Web_Service.users.CustomUserDetails;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PromoCodeService {
    private final PromoCodeRepository promoCodeRepository;
    private final RewardService rewardService;
    private final UserPromoCodeRepository userPromoCodeRepository;
    private final RewardRepository rewardRepository;

    public PromoCodeService (PromoCodeRepository promoCodeRepository, RewardService rewardService, UserPromoCodeRepository userPromoCodeRepository, RewardRepository rewardRepository) {
        this.promoCodeRepository = promoCodeRepository;
        this.rewardService = rewardService;
        this.userPromoCodeRepository = userPromoCodeRepository;
        this.rewardRepository = rewardRepository;
    }

    public String validate (String code) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        PromoCode promoCode = promoCodeRepository.findByPromoCode(code).orElse(null);

        if (promoCode == null) {
            return "Не удалось найти промокод!";
        }

        boolean alredyUsed = userPromoCodeRepository.existsByUserAndPromoCode(customUserDetails.getUser(), promoCode);

        if (alredyUsed) {
            return "Вы уже использовали этот промокод!";
        }

        if (promoCode.getPromoCodeStatus() != PromoCodeStatus.AVAILABLE) {
            return "Промокод недоступен или не существует!";
        }

        if (promoCode.getExpiresAt() != null && promoCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            return "Срок действия промокода истек!";
        }

        if (promoCode.getCount() <= 0) {
            return "Промокод больше нельзя использовать!";
        }

        return null;
    }

    public String validateCreateData (PromoCodeCreateDto createPromoCode) {
        if (promoCodeRepository.findByPromoCode(createPromoCode.getPromoCode()).isPresent()) {
            return "Такой промокод уже существует!";
        }

        if (createPromoCode.getPromoCode() == null || createPromoCode.getPromoCode().isBlank()) {
            return "Промокод не может быть пустым!";
        }

        if (createPromoCode.getExpiresAt() == null) {
            return "Промокод не может иметь дату истечения null!";
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

        if (!createPromoCode.getUrl() && createPromoCode.getMoney() == null && createPromoCode.getRole() == null) {
            return "Нельзя создать промокод без награды!";
        }

        return null;
    }

    @Transactional
    public ResponseEntity<?> update (PromoCode promoCode, User user) {
        if (promoCode.getCount() <= 0) {
            return ResponseEntity.status(400).body(Map.of("message", "Невозможно использовать этот промокод!"));
        }

        UserPromoCode userPromoCode = new UserPromoCode();
        userPromoCode.setUser(user);
        userPromoCode.setPromoCode(promoCode);
        promoCode.setCount(promoCode.getCount() - 1);
        promoCodeRepository.save(promoCode);
        userPromoCodeRepository.save(userPromoCode);
        return rewardService.giveRewardUser(user, promoCode);
    }

    public boolean isModified (PromoCodeUpdateDto updatePromoCode, PromoCode currentPromoCode) {
        boolean flag = false;

        if (updatePromoCode.getPromoCode() != null && !updatePromoCode.getPromoCode().isBlank() &&  !updatePromoCode.getPromoCode().equals(currentPromoCode.getPromoCode())) {
            currentPromoCode.setPromoCode(updatePromoCode.getPromoCode());
            flag = true;
        }

        if (updatePromoCode.getCount() != null && updatePromoCode.getCount() > 0 && updatePromoCode.getCount() != currentPromoCode.getCount()) {
            currentPromoCode.setCount(updatePromoCode.getCount());
            flag = true;
        }

        if (updatePromoCode.getExpiresAt() != null && !updatePromoCode.getExpiresAt().equals(currentPromoCode.getExpiresAt())) {
            currentPromoCode.setExpiresAt(updatePromoCode.getExpiresAt());
            flag = true;
        }

        if (updatePromoCode.getPromoCodeType() != null && PromoCodeType.valueOf(updatePromoCode.getPromoCodeType()) != currentPromoCode.getPromoCodeType()) {
            PromoCodeType type;
            try {
                type = PromoCodeType.valueOf(updatePromoCode.getPromoCodeType());
            }

            catch (IllegalArgumentException e) {
                return false;
            }

            currentPromoCode.setPromoCodeType(type);
            flag = true;
        }

        if (updatePromoCode.getPromoCodeStatus() != null && PromoCodeStatus.valueOf(updatePromoCode.getPromoCodeStatus()) != currentPromoCode.getPromoCodeStatus()) {
            PromoCodeStatus status;
            try {
                status = PromoCodeStatus.valueOf(updatePromoCode.getPromoCodeStatus());
            }

            catch (IllegalArgumentException e) {
                return false;
            }

            currentPromoCode.setPromoCodeStatus(status);
            flag = true;
        }

        return flag;
    }

    @Transactional
    public ResponseEntity<?> usePromoCode (PromoCodeUseDto promoCodeDto, User user) {
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

    public PromoCode getPromoCode (PromoCodeUseDto promoCodeDto, User user) {
        return promoCodeRepository.findByPromoCode(promoCodeDto.getPromoCode()).orElse(null);
    }

    @Transactional
    public String create (PromoCodeCreateDto promoCodeCreateDto, User user) {
        String message = validateCreateData(promoCodeCreateDto);

        if (message != null) {
            return message;
        }

        PromoCode promoCode = new PromoCode();
        promoCode.setPromoCode(promoCodeCreateDto.getPromoCode().trim());
        promoCode.setCount(promoCodeCreateDto.getCount());
        promoCode.setCreatedAt(LocalDateTime.now());
        promoCode.setExpiresAt(promoCodeCreateDto.getExpiresAt());
        promoCode.setAdministrator(user);
        promoCode.setPromoCodeType(promoCodeCreateDto.getPromoCodeType());
        promoCode.setPromoCodeStatus(promoCodeCreateDto.getPromoCodeStatus());

        Reward reward = new Reward();
        if (promoCodeCreateDto.getUrl() && promoCodeCreateDto.getPromoCodeType() == PromoCodeType.PAGE) {
            reward.setUrl(UUID.randomUUID().toString());
            reward.setPromoCode(promoCode);
        } else if (promoCodeCreateDto.getMoney() != null && promoCodeCreateDto.getMoney() >= 0 && promoCodeCreateDto.getPromoCodeType() == PromoCodeType.MONEY) {
            reward.setPromoCode(promoCode);
            reward.setBalance(promoCodeCreateDto.getMoney());
        } else if (promoCodeCreateDto.getRole() != null && !promoCodeCreateDto.getRole().isBlank() && promoCodeCreateDto.getPromoCodeType() == PromoCodeType.ROLE) {
            reward.setPromoCode(promoCode);
            Role role = null;
            if (promoCodeCreateDto.getRole() != null) {
                try {
                    role = Role.valueOf(promoCodeCreateDto.getRole());
                    promoCodeCreateDto.setRole(role.toString());
                } catch (IllegalArgumentException e) {
                    return "Роль не определена!";
                }
            }

            reward.setPromoCode(promoCode);
            reward.setRole(role);
        } else {
            return "Ошибка! Возможное несоответствие типа промокода и указанной награды!";
        }

        if (reward.getPromoCode() == null) {
            return "Вы не добавили награду!";
        }

        promoCodeRepository.save(promoCode);
        rewardRepository.save(reward);
        return null;
    }

    public List<PromoCodeDto> getPromoCodes () {
        List<PromoCode> list = promoCodeRepository.findAll();

        if (list.isEmpty()) {
            return List.of();
        }

        return list.stream().map(promoCode -> new PromoCodeDto(
                promoCode.getPromoCode(),
                promoCode.getCount(),
                promoCode.getCreatedAt(),
                promoCode.getExpiresAt(),
                promoCode.getAdministrator(),
                promoCode.getPromoCodeType(),
                promoCode.getPromoCodeStatus()
        )).toList();
    }

    public PromoCodeDto getPromoCode (int id) {
        PromoCode promoCode = promoCodeRepository.findById(id).orElse(null);

        if (promoCode == null) {
            return null;
        }

        return new PromoCodeDto(
                promoCode.getPromoCode(),
                promoCode.getCount(),
                promoCode.getCreatedAt(),
                promoCode.getExpiresAt(),
                promoCode.getAdministrator(),
                promoCode.getPromoCodeType(),
                promoCode.getPromoCodeStatus()
        );
    }

    public ResponseEntity<?> updatePromoCode(int id, PromoCodeUpdateDto updateDto) {
        PromoCode promoCode = promoCodeRepository.findById(id).orElse(null);

        if (promoCode == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Промокод не найден!"));
        }

        if (updateDto.getPromoCode().length() > 5 && updateDto.getPromoCode().length() < 30) {
            return ResponseEntity.badRequest().body(Map.of("message", "Промокод не может иметь длину меньше 5 или больше 30 символов!"));
        }

        boolean changes = isModified (updateDto, promoCode);

        if (!changes) {
            return ResponseEntity.badRequest().body(Map.of("message", "Не были внесены изменения!"));
        }

        promoCodeRepository.save(promoCode);
        return ResponseEntity.ok().body(Map.of("message", "Изменения успешно внесены!"));
    }
}