package com.example.senior_project.dto;

import java.util.List;

import com.example.senior_project.model.ProductStatus;

import lombok.Data;

@Data
public class ProductUpdateRequest {
    private String title;

    private String description;
    private Double price;
    private Long categoryId;
    private Integer stock;
    private List<String> images;
    private List<String> tags;
    private String shippingDetails;
    private ProductStatus status;
}