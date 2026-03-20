package com.example.Web_Service.repository;

import com.example.Web_Service.model.entity.Reward;
import com.example.Web_Service.model.entity.RewardPage;
import com.example.Web_Service.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RewardPageRepository extends JpaRepository<RewardPage, Integer> {
    Optional<RewardPage> findByUser (User user);
    List<RewardPage> findByUrl (String url);
    boolean existsByUserAndReward(User user, Reward reward);
}