package com.example.senior_project.dto;

import com.example.senior_project.model.UserStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserStatusUpdateRequest {
    @NotNull
    private UserStatus status;
    
    @NotBlank
    private String reason;
} 