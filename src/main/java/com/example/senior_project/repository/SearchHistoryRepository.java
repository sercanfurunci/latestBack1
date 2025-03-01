package com.example.senior_project.repository;

import com.example.senior_project.model.SearchHistory;
import com.example.senior_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    List<SearchHistory> findByUserOrderBySearchDateDesc(User user);
    List<SearchHistory> findTop10ByUserOrderBySearchDateDesc(User user);
    List<SearchHistory> findDistinctTop5BySearchTermContainingOrderBySearchDateDesc(String term);
} 