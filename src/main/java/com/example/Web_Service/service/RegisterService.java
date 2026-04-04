package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.auth.RegisterDto;
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
    public User register (RegisterDto registerDto) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());
        user.setPhone(registerDto.getPhone());
        user.setBirthdate(registerDto.getBirthdate());

        return userRepository.save(user);
    }

    public String validateUser (RegisterDto registerDto) {
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            return "Никнейм " + registerDto.getUsername() + " уже зарегистрирован!";
        }

        if (userRepository.findByEmail(registerDto.getEmail()).isPresent()) {
            return "Почта " + registerDto.getEmail() + " уже зарегистрирована!";
        }

        if (userRepository.findByPhone(registerDto.getPhone()).isPresent()) {
            return "Номер телефона " + registerDto.getPhone() + " уже зарегистрирован!";
        }

        return null;
    }
}