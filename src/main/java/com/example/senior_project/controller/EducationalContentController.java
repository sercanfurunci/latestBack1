package com.example.senior_project.controller;

import com.example.senior_project.model.EducationalContent;
import com.example.senior_project.model.User;
import com.example.senior_project.service.EducationalContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/educational-contents")
@RequiredArgsConstructor
public class EducationalContentController {
    private final EducationalContentService educationalContentService;

    @PostMapping
    public ResponseEntity<EducationalContent> createContent(
            @RequestBody EducationalContent content,
            @AuthenticationPrincipal User author) {
        return ResponseEntity.ok(educationalContentService.createContent(content, author));
    }

    @GetMapping
    public ResponseEntity<Page<EducationalContent>> getAllContents(Pageable pageable) {
        return ResponseEntity.ok(educationalContentService.getAllPublishedContent(pageable));
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<EducationalContent> getContentById(@PathVariable Long contentId) {
        return ResponseEntity.ok(educationalContentService.getContentById(contentId));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<EducationalContent>> getContentsByCategory(
            @PathVariable String category) {
        return ResponseEntity.ok(educationalContentService.getContentByCategory(category));
    }

    @GetMapping("/search")
    public ResponseEntity<List<EducationalContent>> searchContents(
            @RequestParam String keyword) {
        return ResponseEntity.ok(educationalContentService.searchContent(keyword));
    }

    @PutMapping("/{contentId}")
    public ResponseEntity<EducationalContent> updateContent(
            @PathVariable Long contentId,
            @RequestBody EducationalContent content,
            @AuthenticationPrincipal User author) {
        return ResponseEntity.ok(educationalContentService.updateContent(contentId, content, author));
    }

    @DeleteMapping("/{contentId}")
    public ResponseEntity<Void> deleteContent(
            @PathVariable Long contentId,
            @AuthenticationPrincipal User author) {
        educationalContentService.deleteContent(contentId, author);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{contentId}/publish")
    public ResponseEntity<Void> publishContent(
            @PathVariable Long contentId,
            @AuthenticationPrincipal User admin) {
        educationalContentService.publishContent(contentId, admin);
        return ResponseEntity.ok().build();
    }
} 