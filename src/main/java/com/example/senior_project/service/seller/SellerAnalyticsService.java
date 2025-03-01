package com.example.senior_project.service.seller;

import com.example.senior_project.model.Order;
import com.example.senior_project.model.Product;
import com.example.senior_project.model.User;
import com.example.senior_project.repository.OrderRepository;
import com.example.senior_project.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerAnalyticsService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public Map<String, Object> getSellerDashboard(User seller, LocalDateTime startDate, LocalDateTime endDate) {
        List<Order> orders = orderRepository.findBySellerAndCreatedAtBetween(seller, startDate, endDate);
        List<Product> products = productRepository.findBySeller(seller);

        double totalRevenue = orders.stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();

        long totalOrders = orders.size();
        
        double averageOrderValue = totalOrders > 0 ? totalRevenue / totalOrders : 0;

        Map<Product, Integer> productSales = new HashMap<>();
        for (Order order : orders) {
            productSales.merge(order.getProduct(), 1, Integer::sum);
        }

        Map<String, Object> analytics = new HashMap<>();
        analytics.put("totalRevenue", totalRevenue);
        analytics.put("totalOrders", totalOrders);
        analytics.put("averageOrderValue", averageOrderValue);
        analytics.put("productSales", productSales);
        analytics.put("totalProducts", products.size());
        analytics.put("viewCounts", getProductViewCounts(products));

        return analytics;
    }

    public Map<Product, Integer> getProductViewCounts(List<Product> products) {
        Map<Product, Integer> viewCounts = new HashMap<>();
        for (Product product : products) {
            viewCounts.put(product, product.getViewCount());
        }
        return viewCounts;
    }

    public Map<String, Object> getProductPerformance(Long productId, User seller) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı"));

        if (!product.getSeller().equals(seller)) {
            throw new RuntimeException("Bu ürünün performans verilerine erişim yetkiniz yok");
        }

        List<Order> productOrders = orderRepository.findByProduct(product);

        Map<String, Object> performance = new HashMap<>();
        performance.put("totalSales", productOrders.size());
        performance.put("viewCount", product.getViewCount() != null ? product.getViewCount() : 0);
        performance.put("averageRating", product.getAverageRating() != null ? product.getAverageRating() : 0.0);
        performance.put("conversionRate", calculateConversionRate(product));

        return performance;
    }

    private double calculateConversionRate(Product product) {
        int viewCount = product.getViewCount() != null ? product.getViewCount() : 0;
        if (viewCount == 0) return 0.0;
        
        long sales = orderRepository.countByProduct(product);
        return (double) sales / viewCount * 100;
    }
} 