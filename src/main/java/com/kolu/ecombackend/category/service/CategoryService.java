package com.kolu.ecombackend.category.service;

import com.kolu.ecombackend.category.model.Category;
import com.kolu.ecombackend.category.model.dto.CategoryRequest;
import com.kolu.ecombackend.category.model.dto.CategoryResponse;
import com.kolu.ecombackend.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private CategoryRepository repository;

    public CategoryResponse createCategory(CategoryRequest request) {
        var category = Category.builder()
                .name(request.name())
                .description(request.description())
                .build();
        return repository
                .save(category)
                .toResponse();
    }

    public List<CategoryResponse> getAllCategories() {
        return repository
                .findAll()
                .stream()
                .map(Category::toResponse)
                .toList();
    }

    public CategoryResponse getCategoryById(Integer id) {
        return repository
                .findById(id)
                .map(Category::toResponse)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public CategoryResponse updateCategory(Integer id, CategoryRequest request) {
        var category = repository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(request.name());
        category.setDescription(request.description());
        return repository
                .save(category)
                .toResponse();
    }

    public void deleteCategory(Integer id) {
        var category = repository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        repository.delete(category);
    }

    public void getCategoryProducts(Integer id) {
        //todo
    }
}
