package com.example.senior_project.controller.buyer;

import com.example.senior_project.dto.OrderRequest;
import com.example.senior_project.model.Order;
import com.example.senior_project.model.User;
import com.example.senior_project.service.buyer.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/buyer/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(
            @Valid @RequestBody OrderRequest request,
            @AuthenticationPrincipal User buyer) {
        try {
            Order order = orderService.createOrder(request, buyer);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(new HashMap<String, String>() {{
                    put("error", e.getMessage());
                }});
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getBuyerOrders(@AuthenticationPrincipal User buyer) {
        return ResponseEntity.ok(orderService.getBuyerOrders(buyer));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderDetails(
            @PathVariable Long orderId,
            @AuthenticationPrincipal User buyer) {
        return ResponseEntity.ok(orderService.getOrderDetails(orderId, buyer));
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(
            @PathVariable Long orderId,
            @AuthenticationPrincipal User buyer) {
        try {
            orderService.cancelOrder(orderId, buyer);
            return ResponseEntity.ok()
                .body(new HashMap<String, String>() {{
                    put("message", "Sipariş başarıyla iptal edildi");
                }});
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(new HashMap<String, String>() {{
                    put("error", e.getMessage());
                }});
        }
    }
} 