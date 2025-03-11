package com.example.senior_project.repository;

import com.example.senior_project.model.SuccessStory;
import com.example.senior_project.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuccessStoryRepository extends JpaRepository<SuccessStory, Long> {
    Page<SuccessStory> findByIsApprovedTrue(Pageable pageable);
    List<SuccessStory> findByAuthor(User author);
    List<SuccessStory> findByCategoryAndIsApprovedTrue(String category);
    List<SuccessStory> findByTitleContainingOrStoryContainingAndIsApprovedTrue(String titleKeyword, String storyKeyword);
} 