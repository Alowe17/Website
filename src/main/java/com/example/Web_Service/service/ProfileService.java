package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.user.UserUpdateDto;
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

    public String validateUser (UserUpdateDto userUpdateDto) {
        if (userUpdateDto.getName().isBlank()) {
            userUpdateDto.setName(null);
        }

        if (userUpdateDto.getUsername().isBlank()) {
            userUpdateDto.setUsername(null);
        }

        if (userUpdateDto.getPassword().isBlank()) {
            userUpdateDto.setPassword(null);
        }

        if (userUpdateDto.getEmail().isBlank()) {
            userUpdateDto.setEmail(null);
        }

        if (userUpdateDto.getPhone().isBlank()) {
            userUpdateDto.setPhone(null);
        }

        if (userRepository.findByUsername(userUpdateDto.getUsername()).isPresent()) {
            return "Никнейм " + userUpdateDto.getUsername() + " уже занят!";
        }

        if (userRepository.findByEmail(userUpdateDto.getEmail()).isPresent()) {
            return "Почта " + userUpdateDto.getEmail() + " уже занята!";
        }

        if (userRepository.findByPhone(userUpdateDto.getPhone()).isPresent()) {
            return "Номер телефона " + userUpdateDto.getPhone() + " уже занят!";
        }

        if (userUpdateDto.getName() == null && userUpdateDto.getUsername() == null && userUpdateDto.getPassword() == null && userUpdateDto.getEmail() == null && userUpdateDto.getPhone() == null) {
            return "Вы не внесли изменения для того, чтобы обновить профиль!";
        }

        return null;
    }

    public void updateUserData (UserUpdateDto userUpdateDto, User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (userUpdateDto.getName() != null) {
            user.setName(userUpdateDto.getName());
        }

        if (userUpdateDto.getUsername() != null) {
            user.setUsername(userUpdateDto.getUsername());
        }

        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }

        if (userUpdateDto.getPhone() != null) {
            user.setPhone(userUpdateDto.getPhone());
        }

        if (userUpdateDto.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(userUpdateDto.getPassword()));
        }

        userRepository.save(user);
    }
}