package com.example.senior_project.service.buyer;

import com.example.senior_project.dto.CommentRequest;
import com.example.senior_project.model.Comment;
import com.example.senior_project.model.Product;
import com.example.senior_project.model.User;
import com.example.senior_project.repository.CommentRepository;
import com.example.senior_project.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Comment addComment(CommentRequest request, User user) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Comment comment = Comment.builder()
                .product(product)
                .user(user)
                .content(request.getContent())
                .rating(request.getRating())
                .build();

        Comment savedComment = commentRepository.save(comment);
        updateProductRating(product);
        
        return savedComment;
    }

    private void updateProductRating(Product product) {
        Double averageRating = commentRepository.getAverageRatingByProduct(product);
        product.setAverageRating(averageRating);
        productRepository.save(product);
    }
} 