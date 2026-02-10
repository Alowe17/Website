package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.UpdateUserRequestDto;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final UserRepository userRepository;

    public ProfileService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String validateUser (UpdateUserRequestDto updateUserRequestDto) {
        if (updateUserRequestDto.getName().isBlank()) {
            updateUserRequestDto.setName(null);
        }

        if (updateUserRequestDto.getUsername().isBlank()) {
            updateUserRequestDto.setUsername(null);
        }

        if (updateUserRequestDto.getPassword().isBlank()) {
            updateUserRequestDto.setPassword(null);
        }

        if (updateUserRequestDto.getEmail().isBlank()) {
            updateUserRequestDto.setEmail(null);
        }

        if (updateUserRequestDto.getPhone().isBlank()) {
            updateUserRequestDto.setPhone(null);
        }

        if (userRepository.findByUsername(updateUserRequestDto.getUsername()).isPresent()) {
            return "Никнейм " + updateUserRequestDto.getUsername() + " уже занят!";
        }

        if (userRepository.findByEmail(updateUserRequestDto.getEmail()).isPresent()) {
            return "Почта " + updateUserRequestDto.getEmail() + " уже занята!";
        }

        if (userRepository.findByPhone(updateUserRequestDto.getPhone()).isPresent()) {
            return "Номер телефона " + updateUserRequestDto.getPhone() + " уже занят!";
        }

        if (updateUserRequestDto.getName() == null && updateUserRequestDto.getUsername() == null && updateUserRequestDto.getPassword() == null && updateUserRequestDto.getEmail() == null && updateUserRequestDto.getPhone() == null) {
            return "Вы не внесли изменения для того, чтобы обновить профиль!";
        }

        return null;
    }

    public void updateUserData (UpdateUserRequestDto updateUserRequestDto, User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (updateUserRequestDto.getName() != null) {
            user.setName(updateUserRequestDto.getName());
        }

        if (updateUserRequestDto.getUsername() != null) {
            user.setUsername(updateUserRequestDto.getUsername());
        }

        if (updateUserRequestDto.getEmail() != null) {
            user.setEmail(updateUserRequestDto.getEmail());
        }

        if (updateUserRequestDto.getPhone() != null) {
            user.setPhone(updateUserRequestDto.getPhone());
        }

        if (updateUserRequestDto.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(updateUserRequestDto.getPassword()));
        }

        userRepository.save(user);
    }
}