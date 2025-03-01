package com.example.senior_project.controller.seller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.senior_project.model.Comment;
import com.example.senior_project.model.User;
import com.example.senior_project.service.seller.SellerCommentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seller/comments")
@RequiredArgsConstructor
public class SellerCommentController {
    private final SellerCommentService sellerCommentService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Comment>> getProductComments(
            @PathVariable Long productId,
            @AuthenticationPrincipal User seller) {
        return ResponseEntity.ok(sellerCommentService.getProductComments(productId, seller));
    }

    @PostMapping("/{commentId}/reply")
    public ResponseEntity<Comment> replyToComment(
            @PathVariable Long commentId,
            @RequestParam String reply,
            @AuthenticationPrincipal User seller) {
        return ResponseEntity.ok(sellerCommentService.replyToComment(commentId, reply, seller));
    }
} 