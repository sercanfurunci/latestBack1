package com.example.senior_project.controller.buyer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.senior_project.model.SearchHistory;
import com.example.senior_project.model.User;
import com.example.senior_project.service.buyer.SearchHistoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/buyer/search-history")
@RequiredArgsConstructor
public class SearchHistoryController {
    private final SearchHistoryService searchHistoryService;


    @PostMapping("/save")
    public ResponseEntity<SearchHistory> saveSearch(@RequestParam String searchTerm, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(searchHistoryService.saveSearch(searchTerm, user));
    }

    @GetMapping("/recent")
    public ResponseEntity<List<SearchHistory>> getRecentSearches(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(searchHistoryService.getRecentSearches(user));
    }


    @GetMapping("/suggestions")
    public ResponseEntity<List<SearchHistory>> getSuggestions(@RequestParam String term) {
        return ResponseEntity.ok(searchHistoryService.getSuggestions(term));
    }

    @DeleteMapping
    public ResponseEntity<Void> clearSearchHistory(@AuthenticationPrincipal User user) {
        searchHistoryService.clearSearchHistory(user);
        return ResponseEntity.ok().build();
    }
} 