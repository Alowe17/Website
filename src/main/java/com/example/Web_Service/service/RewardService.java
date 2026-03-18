package com.example.Web_Service.service;

import com.example.Web_Service.model.entity.PromoCode;
import com.example.Web_Service.model.entity.Reward;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.enums.PromoCodeType;
import com.example.Web_Service.repository.RewardRepository;
import com.example.Web_Service.repository.UserRepository;
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

    public RewardService (RewardRepository rewardRepository, UserRepository userRepository) {
        this.rewardRepository = rewardRepository;
        this.userRepository = userRepository;
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
                    return givePageReward(list);
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

    public ResponseEntity<?> givePageReward (List<Reward> list) {
        Reward reward = list.get(0);
        return ResponseEntity.ok().body(Map.of("message", reward.getUrl()));
    }

    public ResponseEntity<?> giveRoleReward (User user, PromoCode promoCode, List<Reward> list) {
        Reward reward = list.get(0);

        user.setRole(reward.getRole());
        userRepository.save(user);
        return ResponseEntity.ok().body(Map.of("message", "Промокод был успешно активирован!"));
    }
}