package com.example.senior_project.service.seller;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.example.senior_project.model.Comment;
import com.example.senior_project.model.Product;
import com.example.senior_project.repository.CommentRepository;
import com.example.senior_project.repository.ProductRepository;
import com.example.senior_project.model.User;

@Service
@RequiredArgsConstructor
public class SellerCommentService {
    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;

    public List<Comment> getProductComments(Long productId, User seller) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getSeller().equals(seller)) {
            throw new RuntimeException("Not authorized to view these comments");
        }

        return commentRepository.findByProduct(product);
    }

    @Transactional
    public Comment replyToComment(Long commentId, String reply, User seller) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getProduct().getSeller().equals(seller)) {
            throw new RuntimeException("Not authorized to reply to this comment");
        }

        comment.setSellerReply(reply);
        return commentRepository.save(comment);
    }
} 