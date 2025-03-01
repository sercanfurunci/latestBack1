package com.example.senior_project.controller.buyer;

import com.example.senior_project.model.Product;
import com.example.senior_project.service.buyer.ProductViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductViewController {
    private final ProductViewService productViewService;

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(Pageable pageable) {
        return ResponseEntity.ok(productViewService.getAllProducts(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(productViewService.searchProducts(keyword));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productViewService.getProductsByCategory(categoryId));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        return ResponseEntity.ok(productViewService.filterByPriceRange(minPrice, maxPrice));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductDetails(@PathVariable Long productId) {
        return ResponseEntity.ok(productViewService.getProductDetails(productId));
    }
} 