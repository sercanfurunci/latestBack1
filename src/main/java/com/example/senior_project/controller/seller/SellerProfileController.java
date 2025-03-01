package com.example.senior_project.controller.seller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.senior_project.dto.SellerProfileUpdateRequest;
import com.example.senior_project.model.User;
import com.example.senior_project.service.seller.SellerProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/seller/profile")
@RequiredArgsConstructor

public class SellerProfileController {
    private final SellerProfileService sellerProfileService;

    @PutMapping
    public ResponseEntity<User> updateProfile(
            @Valid @RequestBody SellerProfileUpdateRequest request,
            @AuthenticationPrincipal User seller) {
        return ResponseEntity.ok(sellerProfileService.updateProfile(request, seller));
    }

    @GetMapping("/{sellerId}")
    public ResponseEntity<User> getProfile(@PathVariable Long sellerId) {
        return ResponseEntity.ok(sellerProfileService.getProfile(sellerId));
    }
} 