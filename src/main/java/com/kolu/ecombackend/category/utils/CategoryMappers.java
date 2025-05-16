package com.kolu.ecombackend.category.utils;

import com.kolu.ecombackend.category.model.Category;
import com.kolu.ecombackend.category.model.dto.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryMappers {
    public CategoryResponse toResponse(Category category) {
        int productCount = category.getProducts() != null ? category.getProducts().size() : 0;
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .productCount(productCount)
                .build();
    }

    public Category toEntity(CategoryResponse categoryResponse) {
        return Category.builder()
                .name(categoryResponse.name())
                .description(categoryResponse.description())
                .build();
    }
}
