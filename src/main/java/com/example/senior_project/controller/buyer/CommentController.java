package com.example.senior_project.controller.buyer;

import com.example.senior_project.dto.CommentRequest;
import com.example.senior_project.model.Comment;
import com.example.senior_project.model.User;
import com.example.senior_project.service.buyer.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/buyer/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> addComment(
            @Valid @RequestBody CommentRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(commentService.addComment(request, user));
    }
} 