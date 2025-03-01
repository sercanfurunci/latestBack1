package com.example.senior_project.service.buyer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.senior_project.repository.SearchHistoryRepository;
import com.example.senior_project.model.SearchHistory;
import com.example.senior_project.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor

public class SearchHistoryService {
    private final SearchHistoryRepository searchHistoryRepository;

    public SearchHistory saveSearch(String searchTerm, User user) {
        SearchHistory searchHistory = SearchHistory.builder()
                .user(user)
                .searchTerm(searchTerm)
                .build();

        return searchHistoryRepository.save(searchHistory);
    }

    public List<SearchHistory> getRecentSearches(User user) {
        return searchHistoryRepository.findTop10ByUserOrderBySearchDateDesc(user);
    }

    public List<SearchHistory> getSuggestions(String term) {
        return searchHistoryRepository.findDistinctTop5BySearchTermContainingOrderBySearchDateDesc(term);
    }

    public void clearSearchHistory(User user) {
        List<SearchHistory> userHistory = searchHistoryRepository.findByUserOrderBySearchDateDesc(user);
        searchHistoryRepository.deleteAll(userHistory);
    }
} 