package com.example.senior_project.service.admin;

import com.example.senior_project.model.User;
import com.example.senior_project.model.UserStatus;
import com.example.senior_project.model.UserType;
import com.example.senior_project.repository.UserRepository;
import com.example.senior_project.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> getUsersByRole(String role, Pageable pageable) {
        return userRepository.findByRole(role, pageable);
    }

    @Transactional
    public User updateUserStatus(Long userId, UserStatus status, String reason) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus(status);
        User savedUser = userRepository.save(user);

        String message = String.format("Hesabınızın durumu %s olarak değiştirildi. Sebep: %s", status, reason);
        notificationService.createSystemNotification(user, message);

        return savedUser;
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        userRepository.delete(user);
    }

    public User getUserDetails(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public User updateUserRole(Long userId, String newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUserType(UserType.valueOf(newRole));
        return userRepository.save(user);
    }
} 