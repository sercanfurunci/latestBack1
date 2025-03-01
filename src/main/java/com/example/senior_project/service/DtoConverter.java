package com.example.senior_project.service;

import org.springframework.stereotype.Service;

import com.example.senior_project.dto.ProductDTO;
import com.example.senior_project.model.Product;

@Service
public class DtoConverter {
    public ProductDTO toProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .status(product.getStatus())
                .images(product.getImages())
                .tags(product.getTags())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .sellerId(product.getSeller().getId())
                .sellerName(product.getSeller().getFirstName() + " " + product.getSeller().getLastName())
                .ingredients(product.getIngredients())
                .preparationTime(product.getPreparationTime())
                .shippingDetails(product.getShippingDetails())
                .type(product.getType())
                .build();
    }
} 