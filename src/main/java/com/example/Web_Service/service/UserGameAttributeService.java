package com.example.Web_Service.service;

import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.entity.UserGameAttribute;
import com.example.Web_Service.repository.UserGameAttributeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGameAttributeService {
    private final UserGameAttributeRepository userGameAttributeRepository;

    public UserGameAttributeService (UserGameAttributeRepository userGameAttributeRepository) {
        this.userGameAttributeRepository = userGameAttributeRepository;
    }

    public List<UserGameAttribute> getUserGameAttributeByUserList (User user) {
        List<UserGameAttribute> list = userGameAttributeRepository.findByUser(user);

        if (list.isEmpty()) {
            return List.of();
        }

        return list;
    }

    public void updateDataUserGameAttribute (User user, String effectKey, int effectValue) {
        UserGameAttribute attribute = userGameAttributeRepository.findByUserAndEffectKey(user, effectKey).orElse(null);

        if (attribute == null) {
            attribute = new UserGameAttribute();
            attribute.setUser(user);
            attribute.setEffectKey(effectKey);
            attribute.setEffectValue(effectValue);
        } else {
            attribute.setEffectValue(attribute.getEffectValue() + effectValue);
        }

        userGameAttributeRepository.save(attribute);
    }

    public UserGameAttribute getAttribute (User user, String requiredKey) {
        return userGameAttributeRepository.findByUserAndEffectKey(user, requiredKey).orElse(null);
    }
}