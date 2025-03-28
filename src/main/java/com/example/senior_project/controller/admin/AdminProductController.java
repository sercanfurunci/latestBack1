package com.example.senior_project.controller.admin;

import com.example.senior_project.dto.ProductReviewRequest;
import com.example.senior_project.dto.ProductUpdateRequest;
import com.example.senior_project.model.Product;
import com.example.senior_project.service.admin.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin/products")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {
    private final AdminProductService adminProductService;

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            Pageable pageable) {
        return ResponseEntity.ok(adminProductService.getProductsByCategoryAndSearch(category, search, pageable));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductDetails(@PathVariable Long productId) {
        return ResponseEntity.ok(adminProductService.getProductDetails(productId));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductUpdateRequest request) {
        return ResponseEntity.ok(adminProductService.updateProduct(productId, request));
    }

    @PutMapping("/{productId}/status")
    public ResponseEntity<Product> updateProductStatus(
            @PathVariable Long productId,
            @Valid @RequestBody ProductReviewRequest request) {
        return ResponseEntity.ok(adminProductService.updateProductStatus(productId, request.getMessage()));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long productId,
            @RequestParam String reason) {
        adminProductService.deleteProduct(productId, reason);
        return ResponseEntity.ok().build();
    }

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
}