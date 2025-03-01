package com.example.senior_project.controller;

import com.example.senior_project.dto.RoleAssignmentRequest;
import com.example.senior_project.model.User;
import com.example.senior_project.model.UserType;
import com.example.senior_project.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/assign")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> assignRole(@Valid @RequestBody RoleAssignmentRequest request) {
        roleService.assignRole(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserType> getUserRole(@PathVariable Long userId) {
        return ResponseEntity.ok(roleService.getUserRole(userId));
    }

    @GetMapping("/users/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable UserType role) {
        return ResponseEntity.ok(roleService.getUsersByRole(role));
    }
} 