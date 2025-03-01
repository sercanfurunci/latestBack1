package com.example.senior_project.service.seller;

import com.example.senior_project.model.Order;
import com.example.senior_project.model.User;
import com.example.senior_project.model.ShippingStatus;
import com.example.senior_project.model.OrderStatus;
import com.example.senior_project.repository.OrderRepository;
import com.example.senior_project.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingService {
    private final OrderRepository orderRepository;
    private final NotificationService notificationService;

    @Transactional
    public Order updateTrackingNumber(Long orderId, String trackingNumber, User seller) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getSeller().equals(seller)) {
            throw new RuntimeException("Not authorized to update this order's shipping");
        }

        order.setTrackingNumber(trackingNumber);
        Order savedOrder = orderRepository.save(order);
        
        String message = "Siparişiniz kargoya verildi. Takip numarası: " + trackingNumber;
        notificationService.notifyBuyer(order.getBuyer(), message, order);
        
        return savedOrder;
    }

    @Transactional
    public Order updateShippingStatus(Long orderId, ShippingStatus status, User seller) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı"));

            if (!order.getSeller().equals(seller)) {
                throw new RuntimeException("Bu siparişin kargo durumunu güncelleme yetkiniz yok");
            }

            order.setShippingStatus(status);
            order.setStatus(OrderStatus.SHIPPED);
            Order savedOrder = orderRepository.save(order);
            
            String message = "Siparişinizin kargo durumu güncellendi: " + status;
            notificationService.notifyBuyer(order.getBuyer(), message, savedOrder);
            
            return savedOrder;
        } catch (Exception e) {
            log.error("Kargo durumu güncellenirken hata: {}", e.getMessage());
            throw new RuntimeException("Kargo durumu güncellenirken bir hata oluştu");
        }
    }

    public Order getShippingDetails(Long orderId, User seller) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getSeller().equals(seller)) {
            throw new RuntimeException("Not authorized to view this order's shipping details");
        }

        return order;
    }
} 