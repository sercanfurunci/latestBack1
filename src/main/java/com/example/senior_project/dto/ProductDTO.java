package com.example.senior_project.dto;

import java.util.List;
import java.util.Set;

import com.example.senior_project.model.ProductStatus;
import com.example.senior_project.model.ProductType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private Integer stock;
    private ProductStatus status;
    private List<String> images;
    private Set<String> tags;
    private Long categoryId;
    private String categoryName;
    private Long sellerId;
    private String sellerName;
    private String ingredients;
    private String preparationTime;
    private String shippingDetails;
    private ProductType type;
} 