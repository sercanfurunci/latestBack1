package com.example.senior_project.controller;

import com.example.senior_project.model.SuccessStory;
import com.example.senior_project.model.StoryComment;
import com.example.senior_project.model.User;
import com.example.senior_project.service.SuccessStoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stories")
@RequiredArgsConstructor
public class SuccessStoryController {
    private final SuccessStoryService successStoryService;

    @PostMapping
    public ResponseEntity<SuccessStory> shareStory(
            @RequestBody SuccessStory story,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(successStoryService.shareStory(story, user));
    }

    @GetMapping
    public ResponseEntity<Page<SuccessStory>> getAllStories(Pageable pageable) {
        return ResponseEntity.ok(successStoryService.getAllApprovedStories(pageable));
    }

    @GetMapping("/{storyId}")
    public ResponseEntity<SuccessStory> getStoryById(@PathVariable Long storyId) {
        return ResponseEntity.ok(successStoryService.getStoryById(storyId));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<SuccessStory>> getStoriesByCategory(
            @PathVariable String category) {
        return ResponseEntity.ok(successStoryService.getStoriesByCategory(category));
    }

    @PostMapping("/{storyId}/comments")
    public ResponseEntity<StoryComment> addComment(
            @PathVariable Long storyId,
            @RequestBody String comment,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(successStoryService.addComment(storyId, comment, user));
    }

    @GetMapping("/{storyId}/comments")
    public ResponseEntity<List<StoryComment>> getStoryComments(
            @PathVariable Long storyId) {
        return ResponseEntity.ok(successStoryService.getStoryComments(storyId));
    }

    @PostMapping("/{storyId}/support")
    public ResponseEntity<Void> supportStory(
            @PathVariable Long storyId,
            @AuthenticationPrincipal User user) {
        successStoryService.supportStory(storyId, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{storyId}/support")
    public ResponseEntity<Void> removeSupport(
            @PathVariable Long storyId,
            @AuthenticationPrincipal User user) {
        successStoryService.removeSupport(storyId, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<SuccessStory>> searchStories(
            @RequestParam String keyword) {
        return ResponseEntity.ok(successStoryService.searchStories(keyword));
    }

    @PutMapping("/{storyId}")
    public ResponseEntity<SuccessStory> updateStory(
            @PathVariable Long storyId,
            @RequestBody SuccessStory story,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(successStoryService.updateStory(storyId, story, user));
    }

    @DeleteMapping("/{storyId}")
    public ResponseEntity<Void> deleteStory(
            @PathVariable Long storyId,
            @AuthenticationPrincipal User user) {
        successStoryService.deleteStory(storyId, user);
        return ResponseEntity.ok().build();
    }
} 