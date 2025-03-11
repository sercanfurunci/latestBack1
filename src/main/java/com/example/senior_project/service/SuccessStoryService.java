package com.example.senior_project.service;

import com.example.senior_project.model.SuccessStory;
import com.example.senior_project.model.StoryComment;
import com.example.senior_project.model.User;
import com.example.senior_project.model.UserType;
import com.example.senior_project.repository.SuccessStoryRepository;
import com.example.senior_project.repository.StoryCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SuccessStoryService {
    private final SuccessStoryRepository successStoryRepository;
    private final StoryCommentRepository commentRepository;
    private final NotificationService notificationService;

    @Transactional
    public SuccessStory shareStory(SuccessStory story, User author) {
        if (author.getUserType() != UserType.SELLER) {
            throw new RuntimeException("Sadece satıcılar başarı hikayesi paylaşabilir");
        }

        story.setAuthor(author);
        story.setApproved(false);
        return successStoryRepository.save(story);
    }

    @Transactional(readOnly = true)
    public SuccessStory getStoryById(Long storyId) {
        return successStoryRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Hikaye bulunamadı"));
    }

    @Transactional
    public SuccessStory updateStory(Long storyId, SuccessStory updatedStory, User author) {
        if (author.getUserType() != UserType.SELLER) {
            throw new RuntimeException("Sadece satıcılar başarı hikayelerini düzenleyebilir");
        }

        SuccessStory existingStory = successStoryRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Hikaye bulunamadı"));

        if (!existingStory.getAuthor().equals(author)) {
            throw new RuntimeException("Bu hikayeyi düzenleme yetkiniz yok");
        }

        existingStory.setTitle(updatedStory.getTitle());
        existingStory.setStory(updatedStory.getStory());
        existingStory.setImages(updatedStory.getImages());
        existingStory.setCategory(updatedStory.getCategory());

        return successStoryRepository.save(existingStory);
    }

    @Transactional
    public void approveStory(Long storyId, User admin) {
        SuccessStory story = successStoryRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Hikaye bulunamadı"));

        story.setApproved(true);
        successStoryRepository.save(story);

        notificationService.createSystemNotification(
            story.getAuthor(),
            "Hikayeniz onaylandı ve yayınlandı: " + story.getTitle()
        );
    }

    @Transactional(readOnly = true)
    public Page<SuccessStory> getAllApprovedStories(Pageable pageable) {
        return successStoryRepository.findByIsApprovedTrue(pageable);
    }

    @Transactional(readOnly = true)
    public List<SuccessStory> getStoriesByCategory(String category) {
        return successStoryRepository.findByCategoryAndIsApprovedTrue(category);
    }

    @Transactional
    public StoryComment addComment(Long storyId, String comment, User user) {
        SuccessStory story = successStoryRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Hikaye bulunamadı"));

        StoryComment newComment = StoryComment.builder()
                .story(story)
                .user(user)
                .comment(comment)
                .build();

        StoryComment savedComment = commentRepository.save(newComment);

        // Hikaye sahibine bildirim gönder
        if (!story.getAuthor().equals(user)) {
            notificationService.createSystemNotification(
                story.getAuthor(),
                user.getFirstName() + " " + user.getLastName() + " hikayenize destek mesajı yazdı"
            );
        }

        return savedComment;
    }

    @Transactional
    public void supportStory(Long storyId, User user) {
        SuccessStory story = successStoryRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Hikaye bulunamadı"));

        if (!story.getSupporters().contains(user)) {
            story.getSupporters().add(user);
            story.setSupportCount(story.getSupportCount() + 1);
            successStoryRepository.save(story);

            if (!story.getAuthor().equals(user)) {
                notificationService.createSystemNotification(
                    story.getAuthor(),
                    user.getFirstName() + " " + user.getLastName() + " hikayenizi destekledi"
                );
            }
        }
    }

    @Transactional
    public void removeSupport(Long storyId, User user) {
        SuccessStory story = successStoryRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Hikaye bulunamadı"));

        if (story.getSupporters().contains(user)) {
            story.getSupporters().remove(user);
            story.setSupportCount(story.getSupportCount() - 1);
            successStoryRepository.save(story);
        }
    }

    @Transactional(readOnly = true)
    public List<StoryComment> getStoryComments(Long storyId) {
        return commentRepository.findByStoryIdOrderByCreatedAtDesc(storyId);
    }

    @Transactional
    public void deleteStory(Long storyId, User author) {
        if (author.getUserType() != UserType.SELLER) {
            throw new RuntimeException("Sadece satıcılar başarı hikayelerini silebilir");
        }

        SuccessStory story = successStoryRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Hikaye bulunamadı"));

        if (!story.getAuthor().equals(author)) {
            throw new RuntimeException("Bu hikayeyi silme yetkiniz yok");
        }

        successStoryRepository.delete(story);
    }

    @Transactional(readOnly = true)
    public List<SuccessStory> searchStories(String keyword) {
        return successStoryRepository.findByTitleContainingOrStoryContainingAndIsApprovedTrue(keyword, keyword);
    }
} 