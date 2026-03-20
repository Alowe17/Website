package com.example.Web_Service.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reward_page")
public class RewardPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reward_id")
    private Reward reward;
    private String url;

    public RewardPage (int id, User user, Reward reward, String url) {
        this.id = id;
        this.user = user;
        this.reward = reward;
        this.url = url;
    }

    public RewardPage() {}

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Reward getReward() {
        return reward;
    }

    public String getUrl() {
        return url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}