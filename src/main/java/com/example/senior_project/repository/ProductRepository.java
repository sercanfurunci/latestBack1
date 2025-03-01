package com.example.senior_project.repository;

import com.example.senior_project.model.Product;
import com.example.senior_project.model.ProductStatus;
import com.example.senior_project.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByStatus(ProductStatus status, Pageable pageable);
    List<Product> findByTitleContainingOrDescriptionContaining(String title, String description);
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    List<Product> findBySeller(User seller);
} 