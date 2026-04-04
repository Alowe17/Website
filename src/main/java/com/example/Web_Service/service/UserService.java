package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.adminDto.user.request.UserUpdateDto;
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

    public String userDataValidator (UserUpdateDto userUpdateDto) {
        if (userUpdateDto.getUsername() != null && userRepository.findByUsername(userUpdateDto.getUsername()).isPresent()) {
            return "Никнейм " + userUpdateDto.getUsername() + " уже используется!";
        }

        if (userUpdateDto.getEmail() != null && userRepository.findByEmail(userUpdateDto.getEmail()).isPresent()) {
            return "Почта " + userUpdateDto.getEmail() + " уже используется!";
        }

        if (userUpdateDto.getPhone() != null && userRepository.findByPhone(userUpdateDto.getPhone()).isPresent()) {
            return "Номер телефона " + userUpdateDto.getPhone() + " уже используется!";
        }

        return null;
    }

    public String updateUserData (UserUpdateDto userUpdateDto, User user) {
        if (userUpdateDto.getName() == null && userUpdateDto.getUsername() == null &&
                userUpdateDto.getEmail() == null && userUpdateDto.getPhone() == null &&
                userUpdateDto.getRole() == user.getRole() && userUpdateDto.getBalance() == user.getBalance()) {
            return "Вы не внесли изменения. Сохранять нечего!";
        }

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

        if (userUpdateDto.getRole() != user.getRole()) {
            user.setRole(userUpdateDto.getRole());
        }

        if (userUpdateDto.getBalance() != user.getBalance()) {
            user.setBalance(userUpdateDto.getBalance());
        }

        userRepository.save(user);
        return null;
    }
}