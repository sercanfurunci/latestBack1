package com.example.senior_project.dto;

import com.example.senior_project.model.UserType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleAssignmentRequest {
    @NotNull
    private Long userId;
    
    @NotNull
    private UserType newRole;
} 