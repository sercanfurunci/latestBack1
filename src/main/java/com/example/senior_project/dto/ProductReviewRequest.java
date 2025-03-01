package com.example.senior_project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductReviewRequest {
    @NotBlank
    private String message;
} 