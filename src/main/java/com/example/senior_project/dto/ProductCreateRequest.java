package com.example.senior_project.dto;

import java.util.List;

import com.example.senior_project.model.ProductType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductCreateRequest {
    @NotBlank
    private String title;

    @NotBlank
    @Size(min = 10, max = 2000)
    private String description;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    private Long categoryId;

    @NotNull
    @Min(0)
    private Integer stock;

    private List<String> images;
    private List<String> tags;
    private String ingredients;
    private String preparationTime;
    private String shippingDetails;
    private ProductType type;
} 