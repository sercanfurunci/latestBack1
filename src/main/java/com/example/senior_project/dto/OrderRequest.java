package com.example.senior_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequest {
    @NotNull(message = "Ürün ID'si boş olamaz")
    private Long productId;
    
    @NotNull(message = "Teklif ID'si boş olamaz")
    private Long offerId;
    
    @NotBlank(message = "Teslimat adresi boş olamaz")
    private String shippingAddress;
} 