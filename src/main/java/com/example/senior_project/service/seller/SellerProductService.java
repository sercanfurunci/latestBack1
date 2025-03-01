package com.example.senior_project.service.seller;

import com.example.senior_project.model.Category;
import com.example.senior_project.model.Product;
import com.example.senior_project.model.User;
import com.example.senior_project.model.ProductStatus;
import com.example.senior_project.repository.CategoryRepository;
import com.example.senior_project.repository.ProductRepository;
import com.example.senior_project.dto.ProductCreateRequest;
import com.example.senior_project.dto.ProductUpdateRequest;
import com.example.senior_project.model.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Product createProduct(ProductCreateRequest request, User seller) {
        try {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            Product product = Product.builder()
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .price(request.getPrice())
                    .stock(request.getStock())
                    .category(category)
                    .seller(seller)
                    .status(ProductStatus.AVAILABLE)
                    .images(new ArrayList<>(request.getImages()))
                    .tags(new HashSet<>(request.getTags()))
                    .ingredients(request.getIngredients())
                    .preparationTime(request.getPreparationTime())
                    .shippingDetails(request.getShippingDetails())
                    .type(request.getType())
                    .build();

            return productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Ürün oluşturulurken bir hata oluştu: " + e.getMessage());
        }
    }

    @Transactional
    public Product updateProduct(Long productId, ProductUpdateRequest request, User seller) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getSeller().equals(seller)) {
            throw new RuntimeException("Not authorized to update this product");
        }

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }

        // Update fields if they are not null
        if (request.getTitle() != null) product.setTitle(request.getTitle());
        if (request.getDescription() != null) product.setDescription(request.getDescription());
        if (request.getPrice() != null) product.setPrice(request.getPrice());
        if (request.getImages() != null) product.setImages(request.getImages());
        if (request.getStock() != null) product.setStock(request.getStock());
        if (request.getStatus() != null) product.setStatus(request.getStatus());
        if (request.getTags() != null) product.setTags(new HashSet<>(request.getTags()));
        if (request.getShippingDetails() != null) product.setShippingDetails(request.getShippingDetails());

        return productRepository.save(product);
    }

    public void deleteProduct(Long productId, User seller) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getSeller().equals(seller)) {
            throw new RuntimeException("Not authorized to delete this product");
        }

        product.setStatus(ProductStatus.INACTIVE);
        productRepository.save(product);
    }

    public List<Product> getSellerProducts(User seller) {
        return productRepository.findBySeller(seller);
    }

    public Product getProductDetails(Long productId, User seller) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getSeller().equals(seller)) {
            throw new RuntimeException("Not authorized to view this product");
        }

        return product;
    }
} 