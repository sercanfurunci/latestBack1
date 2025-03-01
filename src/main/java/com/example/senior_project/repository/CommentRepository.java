package com.example.senior_project.repository;

import com.example.senior_project.model.Comment;
import com.example.senior_project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT AVG(c.rating) FROM Comment c WHERE c.product = :product")
    Double getAverageRatingByProduct(Product product);
    
    List<Comment> findByProduct(Product product);
} 