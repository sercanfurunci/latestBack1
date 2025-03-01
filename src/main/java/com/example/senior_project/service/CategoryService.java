package com.example.senior_project.service;

import com.example.senior_project.model.Category;
import com.example.senior_project.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı"));
    }

    @Transactional
    public Category createCategory(Category category) {
        if (category.getParent() != null) {
            Category parent = getCategoryById(category.getParent().getId());
            category.setParent(parent);
        }
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = getCategoryById(id);
        
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        
        if (categoryDetails.getParent() != null) {
            Category parent = getCategoryById(categoryDetails.getParent().getId());
            category.setParent(parent);
        }
        
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        if (!category.getSubCategories().isEmpty()) {
            throw new RuntimeException("Alt kategorileri olan bir kategori silinemez");
        }
        if (!category.getProducts().isEmpty()) {
            throw new RuntimeException("Ürünleri olan bir kategori silinemez");
        }
        categoryRepository.delete(category);
    }
} 