package com.example.senior_project.controller.admin;

import com.example.senior_project.dto.ProductReviewRequest;
import com.example.senior_project.model.Product;
import com.example.senior_project.service.admin.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin/products")
@RequiredArgsConstructor
public class AdminProductController {
    private final AdminProductService adminProductService;

    @GetMapping("/pending")
    public ResponseEntity<Page<Product>> getPendingProducts(Pageable pageable) {
        return ResponseEntity.ok(adminProductService.getPendingProducts(pageable));
    }

    @PostMapping("/{productId}/approve")
    public ResponseEntity<Product> approveProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductReviewRequest request) {
        return ResponseEntity.ok(adminProductService.approveProduct(productId, request.getMessage()));
    }

    @PostMapping("/{productId}/reject")
    public ResponseEntity<Product> rejectProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductReviewRequest request) {
        return ResponseEntity.ok(adminProductService.rejectProduct(productId, request.getMessage()));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeProduct(
            @PathVariable Long productId,
            @RequestParam String reason) {
        adminProductService.removeProduct(productId, reason);
        return ResponseEntity.ok().build();
    }
} 