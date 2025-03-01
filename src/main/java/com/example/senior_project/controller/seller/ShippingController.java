package com.example.senior_project.controller.seller;

import com.example.senior_project.model.Order;
import com.example.senior_project.model.User;
import com.example.senior_project.model.ShippingStatus;
import com.example.senior_project.service.seller.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/seller/shipping")
@RequiredArgsConstructor
public class ShippingController {
    private final ShippingService shippingService;

    @PutMapping("/{orderId}/tracking")
    public ResponseEntity<Order> updateTrackingNumber(
            @PathVariable Long orderId,
            @RequestParam String trackingNumber,
            @AuthenticationPrincipal User seller) {
        return ResponseEntity.ok(shippingService.updateTrackingNumber(orderId, trackingNumber, seller));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateShippingStatus(
            @PathVariable Long orderId,
            @RequestParam ShippingStatus status,
            @AuthenticationPrincipal User seller) {
        return ResponseEntity.ok(shippingService.updateShippingStatus(orderId, status, seller));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getShippingDetails(
            @PathVariable Long orderId,
            @AuthenticationPrincipal User seller) {
        return ResponseEntity.ok(shippingService.getShippingDetails(orderId, seller));
    }
} 