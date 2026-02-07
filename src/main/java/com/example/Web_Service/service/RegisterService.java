package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.RegisterRequestDto;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    private final UserRepository userRepository;

    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User register (RegisterRequestDto registerRequestDto) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setName(registerRequestDto.getName());
        user.setUsername(registerRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setEmail(registerRequestDto.getEmail());
        user.setPhone(registerRequestDto.getPhone());
        user.setBirthdate(registerRequestDto.getBirthdate());

        return userRepository.save(user);
    }

    public String validateUser (RegisterRequestDto registerRequestDto) {
        if (userRepository.findByUsername(registerRequestDto.getUsername()).isPresent()) {
            return "Никнейм " + registerRequestDto.getUsername() + " уже зарегистрирован!";
        }

        if (userRepository.findByEmail(registerRequestDto.getEmail()).isPresent()) {
            return "Почта " + registerRequestDto.getEmail() + " уже зарегистрирована!";
        }

        if (userRepository.findByPhone(registerRequestDto.getPhone()).isPresent()) {
            return "Номер телефона " + registerRequestDto.getPhone() + " уже зарегистрирован!";
        }

        return null;
    }
}