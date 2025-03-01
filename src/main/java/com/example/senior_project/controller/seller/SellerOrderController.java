package com.example.senior_project.controller.seller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.senior_project.model.Order;
import com.example.senior_project.model.OrderStatus;
import com.example.senior_project.model.User;
import com.example.senior_project.service.seller.SellerOrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seller/orders")
@RequiredArgsConstructor
public class SellerOrderController {
    private final SellerOrderService sellerOrderService;

    @GetMapping
    public ResponseEntity<List<Order>> getSellerOrders(@AuthenticationPrincipal User seller) {
        return ResponseEntity.ok(sellerOrderService.getSellerOrders(seller));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderDetails(
            @PathVariable Long orderId,
            @AuthenticationPrincipal User seller) {
        return ResponseEntity.ok(sellerOrderService.getOrderDetails(orderId, seller));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus newStatus,
            @AuthenticationPrincipal User seller) {
        return ResponseEntity.ok(sellerOrderService.updateOrderStatus(orderId, newStatus, seller));
    }
} 