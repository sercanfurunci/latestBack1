package com.example.senior_project.dto;

import com.example.senior_project.model.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductStatusUpdateRequest {
    @NotNull(message = "Status is required")
    private ProductStatus status;

    @NotBlank(message = "Message is required")
    private String message;
}