package com.kolu.ecombackend.category.controller;

import com.kolu.ecombackend.category.model.dto.CategoryRequest;
import com.kolu.ecombackend.category.model.dto.CategoryResponse;
import com.kolu.ecombackend.category.service.CategoryService;
import com.kolu.ecombackend.product.model.dto.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category", description = "Category API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService service;

    @Operation(
            summary = "Create a new category",
            description = "Create a new category with the given details"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest) {
        return service.createCategory(categoryRequest);
    }

    @Operation(
            summary = "Get all categories",
            description = "Retrieve a list of all categories"
    )
    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        return service.getAllCategories();
    }

    @Operation(
            summary = "Get a category by ID",
            description = "Retrieve the category with the given ID"
    )
    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Integer id) {
        return service.getCategoryById(id);
    }

    @Operation(
            summary = "Update an existing category",
            description = "Update the category with the given ID and details"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public CategoryResponse updateCategory(
            @PathVariable Integer id,
            @RequestBody CategoryRequest categoryRequest
    ) {
        return service.updateCategory(id, categoryRequest);
    }

    @Operation(
            summary = "Delete a category",
            description = "Delete the category with the given ID"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        service.deleteCategory(id);
    }

    @GetMapping("/{id}/products")
    public List<ProductResponse> getCategoryProducts(@PathVariable Integer id) {
        return  service.getCategoryProducts(id);
    }

}
