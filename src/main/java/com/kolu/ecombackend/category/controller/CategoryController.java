package com.kolu.ecombackend.category.controller;

import com.kolu.ecombackend.category.model.dto.CategoryRequest;
import com.kolu.ecombackend.category.model.dto.CategoryResponse;
import com.kolu.ecombackend.category.service.CategoryService;
import com.kolu.ecombackend.product.model.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest) {
        return service.createCategory(categoryRequest);
    }

    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        return service.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Integer id) {
        return service.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategory(
            @PathVariable Integer id,
            @RequestBody CategoryRequest categoryRequest
    ) {
        return service.updateCategory(id, categoryRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        service.deleteCategory(id);
    }

    @GetMapping("/{id}/products")
    public List<ProductResponse> getCategoryProducts(@PathVariable Integer id) {
        return  service.getCategoryProducts(id);
    }

}
