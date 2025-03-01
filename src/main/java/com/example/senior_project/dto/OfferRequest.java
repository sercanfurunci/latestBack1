package com.example.senior_project.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OfferRequest {
    @NotNull
    private Long productId;

    @NotNull
    @Positive
    private Double offerAmount;

    private String message;
} 