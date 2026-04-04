package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.adminDto.reward.request.RewardUpdateDto;
import com.example.Web_Service.model.dto.adminDto.reward.response.RewardDto;
import com.example.Web_Service.model.entity.PromoCode;
import com.example.Web_Service.model.entity.Reward;
import com.example.Web_Service.model.entity.RewardPage;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.enums.Role;
import com.example.Web_Service.repository.RewardPageRepository;
import com.example.Web_Service.repository.RewardRepository;
import com.example.Web_Service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RewardService {
    private final RewardRepository rewardRepository;
    private final UserRepository userRepository;
    private final RewardPageRepository rewardPageRepository;

    public RewardService (RewardRepository rewardRepository, UserRepository userRepository, RewardPageRepository rewardPageRepository) {
        this.rewardRepository = rewardRepository;
        this.userRepository = userRepository;
        this.rewardPageRepository = rewardPageRepository;
    }

    public ResponseEntity<?> giveRewardUser (User user, PromoCode promoCode) {
        List<Reward> list = rewardRepository.findByPromoCode(promoCode);

        if (list.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Не было найдено награды за данный промокод!"));
        }

        try {
            switch (promoCode.getPromoCodeType()) {
                case MONEY:
                    return giveMoneyReward(user, promoCode, list);
                case ROLE:
                    return giveRoleReward(user, promoCode, list);
                case PAGE:
                    return givePageReward(user, list);
                default:
                    return ResponseEntity.status(400)
                            .body(Map.of("message", "Неизвестный тип промокода!"));
            }
        } catch (Exception e) {
            log.error("Ошибка при применении награды пользователю {}", user.getUsername(), e);
            return ResponseEntity.status(500).body(Map.of("message", "Ошибка при применении промокода!"));
        }
    }

    public ResponseEntity<?> giveMoneyReward (User user, PromoCode promoCode, List<Reward> list) {
        int totalBalance = 0;
        for (Reward reward : list) {
            totalBalance += reward.getBalance();
        }

        user.setBalance(user.getBalance() + totalBalance);
        userRepository.save(user);
        return ResponseEntity.ok().body(Map.of("message", "Промокод был успешно активирован! +".concat(String.valueOf(totalBalance))));
    }

    public ResponseEntity<?> givePageReward (User user, List<Reward> list) {
        Reward reward = list.get(0);

        boolean alreadyHas = rewardPageRepository.existsByUserAndReward(user, reward);
        if (alreadyHas) {
            return ResponseEntity.badRequest().body(Map.of("message", "Эта страница уже была выдана вам ранее!"));
        }

        RewardPage rewardPage = new RewardPage();
        rewardPage.setReward(reward);
        rewardPage.setUser(user);
        rewardPage.setUrl(reward.getUrl());
        rewardPageRepository.save(rewardPage);

        return ResponseEntity.ok().body(Map.of("message", "Используйте ссылку: http://localhost:8080/role-master/promo-code/pages/" + reward.getUrl()));
    }

    public ResponseEntity<?> giveRoleReward (User user, PromoCode promoCode, List<Reward> list) {
        Reward reward = list.get(0);

        user.setRole(reward.getRole());
        userRepository.save(user);
        return ResponseEntity.ok().body(Map.of("message", "Промокод был успешно активирован!"));
    }

     public boolean checkingAccessRights (User user, String url) {
        List<RewardPage> list = rewardPageRepository.findByUrl(url);

        for (RewardPage rewardPage : list) {
            if (rewardPage.getUser().getId() ==  user.getId() || user.getRole() == Role.ADMINISTRATOR) {
                return true;
            }
        }

        return false;
    }

    public List<RewardDto> getRewards (PromoCode promoCode) {
        List<Reward> list = rewardRepository.findByPromoCode(promoCode);

        if (list.isEmpty()) {
            return List.of();
        }

        List<RewardDto> rewardDtoList = list.stream()
                .map(reward -> new RewardDto(
                        reward.getId(),
                        reward.getPromoCode(),
                        reward.getBalance(),
                        reward.getUrl(),
                        reward.getRole()
                ))
                .toList();

        return rewardDtoList;
    }

    @Transactional
    public String update (RewardUpdateDto rewardUpdateDto, int id) {
        Reward reward = rewardRepository.findById(id).orElse(null);

        if (reward == null) {
            return "Не удалось найти награду!";
        }

        boolean changes = isModify (rewardUpdateDto, reward);

        if (!changes) {
            return "Изменений не было внесено!";
        }

        rewardRepository.save(reward);
        return null;
    }

    private boolean isModify (RewardUpdateDto rewardUpdateDto, Reward reward) {
        log.info("Поступил следующий баланс: {}", rewardUpdateDto.getBalance());
        boolean changes = false;
        if (rewardUpdateDto.getBalance() != null && rewardUpdateDto.getBalance() >= 0 && rewardUpdateDto.getBalance() != reward.getBalance()) {
            reward.setBalance(rewardUpdateDto.getBalance());
            log.info("Баланс изменен на {}", reward.getBalance());
            changes = true;
        }

        if (rewardUpdateDto.getUrl() != null && !rewardUpdateDto.getUrl().equals(reward.getUrl())) {
            reward.setUrl(rewardUpdateDto.getUrl());
            log.info("URL изменен на {}", reward.getUrl());
            changes = true;
        }

        if (rewardUpdateDto.getRole() != null && !rewardUpdateDto.getRole().isBlank()) {
            String roleString = rewardUpdateDto.getRole().trim();
            if (reward.getRole() == null || !roleString.equals(reward.getRole().toString())) {
                try {
                    Role role = Role.valueOf(roleString);
                    reward.setRole(role);
                    log.info("Роль изменена на {}", reward.getRole());
                    changes = true;
                } catch (IllegalArgumentException e) {
                    log.warn("Некорректная роль: {}", roleString);
                }
            }
        }

        return changes;
    }
}