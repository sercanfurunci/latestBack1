package com.example.senior_project.service;

import com.example.senior_project.dto.RoleAssignmentRequest;
import com.example.senior_project.model.User;
import com.example.senior_project.model.UserType;
import com.example.senior_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final UserRepository userRepository;

    @Transactional
    public void assignRole(RoleAssignmentRequest request) {
        User currentUser = getCurrentUser();
        if (!currentUser.getUserType().equals(UserType.ADMIN)) {
            throw new AccessDeniedException("Only admins can assign roles");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setUserType(request.getNewRole());
        userRepository.save(user);
    }

    public UserType getUserRole(Long userId) {
        User currentUser = getCurrentUser();
        if (!currentUser.getUserType().equals(UserType.ADMIN) && !currentUser.getId().equals(userId)) {
            throw new AccessDeniedException("You can only view your own role");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return user.getUserType();
    }

    public List<User> getUsersByRole(UserType role) {
        User currentUser = getCurrentUser();
        if (!currentUser.getUserType().equals(UserType.ADMIN)) {
            throw new AccessDeniedException("Only admins can list users by role");
        }

        return userRepository.findByUserType(role);
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Current user not found"));
    }
} 