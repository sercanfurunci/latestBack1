package com.example.senior_project.controller.buyer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.senior_project.model.Favorite;
import com.example.senior_project.model.User;
import com.example.senior_project.service.buyer.FavoriteService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/buyer/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping("/{productId}")
    public ResponseEntity<Favorite> addToFavorites(
            @PathVariable Long productId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(favoriteService.addToFavorites(productId, user));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeFromFavorites(
            @PathVariable Long productId,
            @AuthenticationPrincipal User user) {
        favoriteService.removeFromFavorites(productId, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Favorite>> getUserFavorites(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(favoriteService.getUserFavorites(user));
    }
} 