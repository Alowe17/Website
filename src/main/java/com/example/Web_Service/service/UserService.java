package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.UpdateDataUserRequestDto;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean updateUserPassword (String username, String newPassword) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found!"));

        if (user == null) {
            return false;
        }

        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    public User getUserUsername (String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public String userDataValidator (UpdateDataUserRequestDto updateDataUserRequestDto) {
        if (updateDataUserRequestDto.getUsername() != null && userRepository.findByUsername(updateDataUserRequestDto.getUsername()).isPresent()) {
            return "Никнейм " + updateDataUserRequestDto.getUsername() + " уже используется!";
        }

        if (updateDataUserRequestDto.getEmail() != null && userRepository.findByEmail(updateDataUserRequestDto.getEmail()).isPresent()) {
            return "Почта " + updateDataUserRequestDto.getEmail() + " уже используется!";
        }

        if (updateDataUserRequestDto.getPhone() != null && userRepository.findByPhone(updateDataUserRequestDto.getPhone()).isPresent()) {
            return "Номер телефона " + updateDataUserRequestDto.getPhone() + " уже используется!";
        }

        return null;
    }

    public String updateUserData (UpdateDataUserRequestDto updateDataUserRequestDto, User user) {
        if (updateDataUserRequestDto.getName() == null && updateDataUserRequestDto.getUsername() == null &&
                updateDataUserRequestDto.getEmail() == null && updateDataUserRequestDto.getPhone() == null &&
                updateDataUserRequestDto.getRole() == user.getRole() && updateDataUserRequestDto.getBalance() == user.getBalance()) {
            return "Вы не внесли изменения. Сохранять нечего!";
        }

        if (updateDataUserRequestDto.getName() != null) {
            user.setName(updateDataUserRequestDto.getName());
        }

        if (updateDataUserRequestDto.getUsername() != null) {
            user.setUsername(updateDataUserRequestDto.getUsername());
        }

        if (updateDataUserRequestDto.getEmail() != null) {
            user.setEmail(updateDataUserRequestDto.getEmail());
        }

        if (updateDataUserRequestDto.getPhone() != null) {
            user.setPhone(updateDataUserRequestDto.getPhone());
        }

        if (updateDataUserRequestDto.getRole() != user.getRole()) {
            user.setRole(updateDataUserRequestDto.getRole());
        }

        if (updateDataUserRequestDto.getBalance() != user.getBalance()) {
            user.setBalance(updateDataUserRequestDto.getBalance());
        }

        userRepository.save(user);
        return null;
    }
}