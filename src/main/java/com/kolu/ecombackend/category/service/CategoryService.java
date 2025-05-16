package com.kolu.ecombackend.category.service;

import com.kolu.ecombackend.category.model.Category;
import com.kolu.ecombackend.category.model.dto.CategoryRequest;
import com.kolu.ecombackend.category.model.dto.CategoryResponse;
import com.kolu.ecombackend.category.repository.CategoryRepository;
import com.kolu.ecombackend.category.utils.CategoryMappers;
import com.kolu.ecombackend.product.model.dto.ProductResponse;
import com.kolu.ecombackend.product.repository.ProductRepository;
import com.kolu.ecombackend.product.service.ProductService;
import com.kolu.ecombackend.product.utils.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;
    private final ProductRepository productRepository; // not using service to avoid circular dependency
    private final ProductMapper productMapper;
    private final CategoryMappers categoryMappers;

    public CategoryResponse createCategory(CategoryRequest request) {
        var category = Category.builder()
                .name(request.name())
                .description(request.description())
                .build();
        return categoryMappers
                .toResponse(repository
                        .save(category));
    }

    public List<CategoryResponse> getAllCategories() {
        return repository
                .findAll()
                .stream()
                .map(categoryMappers::toResponse)
                .toList();
    }

    public Category getCategoryEntityById(Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public CategoryResponse getCategoryById(Integer id) {
        return repository
                .findById(id)
                .map(categoryMappers::toResponse)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public CategoryResponse updateCategory(Integer id, CategoryRequest request) {
        var category = repository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(request.name());
        category.setDescription(request.description());
        return categoryMappers
                .toResponse(repository
                        .save(category));
    }

    public void deleteCategory(Integer id) {
        var category = repository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        repository.delete(category);
    }

    public List<ProductResponse> getCategoryProducts(Integer categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }


}
