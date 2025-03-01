package com.example.senior_project.service.admin;

import com.example.senior_project.model.Product;
import com.example.senior_project.model.ProductStatus;
import com.example.senior_project.repository.ProductRepository;
import com.example.senior_project.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminProductService {
    private final ProductRepository productRepository;
    private final NotificationService notificationService;

    public Page<Product> getPendingProducts(Pageable pageable) {
        return productRepository.findByStatus(ProductStatus.PENDING_REVIEW, pageable);
    }

    @Transactional
    public Product approveProduct(Long productId, String message) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setStatus(ProductStatus.AVAILABLE);
        Product savedProduct = productRepository.save(product);

        notificationService.notifySeller(
                product.getSeller(),
                "Ürününüz onaylandı: " + message,
                savedProduct);

        return savedProduct;
    }

    @Transactional
    public Product rejectProduct(Long productId, String reason) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setStatus(ProductStatus.REJECTED);
        Product savedProduct = productRepository.save(product);

        notificationService.notifySeller(
                product.getSeller(),
                "Ürününüz reddedildi. Sebep: " + reason,
                savedProduct);

        return savedProduct;
    }

    @Transactional
    public void removeProduct(Long productId, String reason) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setStatus(ProductStatus.REMOVED);
        productRepository.save(product);

        notificationService.notifySeller(
                product.getSeller(),
                "Ürününüz kaldırıldı. Sebep: " + reason,
                product);
    }
} 