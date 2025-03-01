package com.example.senior_project.controller.seller;

import com.example.senior_project.dto.ProductCreateRequest;
import com.example.senior_project.dto.ProductUpdateRequest;
import com.example.senior_project.dto.ProductDTO;
import com.example.senior_project.model.Product;
import com.example.senior_project.model.User;
import com.example.senior_project.service.seller.SellerProductService;
import com.example.senior_project.service.DtoConverter;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seller/products")
@RequiredArgsConstructor
public class SellerProductController {
    private final SellerProductService sellerProductService;
    private final DtoConverter dtoConverter;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(
            @Valid @RequestBody ProductCreateRequest request,
            @AuthenticationPrincipal User seller) {
        Product product = sellerProductService.createProduct(request, seller);
        return ResponseEntity.ok(dtoConverter.toProductDTO(product));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductUpdateRequest request,
            @AuthenticationPrincipal User seller) {
        return ResponseEntity.ok(sellerProductService.updateProduct(productId, request, seller));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long productId,
            @AuthenticationPrincipal User seller) {
        sellerProductService.deleteProduct(productId, seller);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Product>> getSellerProducts(@AuthenticationPrincipal User seller) {
        return ResponseEntity.ok(sellerProductService.getSellerProducts(seller));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductDetails(
            @PathVariable Long productId,
            @AuthenticationPrincipal User seller) {
        return ResponseEntity.ok(sellerProductService.getProductDetails(productId, seller));
    }
} 