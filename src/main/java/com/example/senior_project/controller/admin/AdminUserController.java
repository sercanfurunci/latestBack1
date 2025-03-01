package com.example.senior_project.controller.admin;

import com.example.senior_project.dto.UserStatusUpdateRequest;
import com.example.senior_project.model.User;
import com.example.senior_project.service.admin.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final AdminUserService adminUserService;

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(adminUserService.getAllUsers(pageable));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<Page<User>> getUsersByRole(
            @PathVariable String role,
            Pageable pageable) {
        return ResponseEntity.ok(adminUserService.getUsersByRole(role, pageable));
    }

    @PutMapping("/{userId}/status")
    public ResponseEntity<User> updateUserStatus(
            @PathVariable Long userId,
            @Valid @RequestBody UserStatusUpdateRequest request) {
        return ResponseEntity.ok(adminUserService.updateUserStatus(
                userId, request.getStatus(), request.getReason()));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        adminUserService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserDetails(@PathVariable Long userId) {
        return ResponseEntity.ok(adminUserService.getUserDetails(userId));
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<User> updateUserRole(
            @PathVariable Long userId,
            @RequestParam String newRole) {
        return ResponseEntity.ok(adminUserService.updateUserRole(userId, newRole));
    }
} 