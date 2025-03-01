package com.example.senior_project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SellerProfileUpdateRequest {
    @NotBlank
    private String shopDescription;
    
    private String biography;
    
    @NotBlank
    private String phoneNumber;
    
    private String profilePicture;
} 