package com.kolu.ecombackend.product.controller;

import com.kolu.ecombackend.product.model.dto.PagedProductResponse;
import com.kolu.ecombackend.product.model.dto.ProductRequest;
import com.kolu.ecombackend.product.model.dto.ProductResponse;
import com.kolu.ecombackend.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @Operation(
            summary = "Create a new product",
            description = "Create a new product with the given details"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ProductResponse saveProduct(@RequestBody ProductRequest productRequest) {
        return productService.saveProduct(productRequest);
    }

    @Operation(
            summary = "Update an existing product",
            description = "Update the product with the given ID and details"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Integer id, @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @Operation(
            summary = "Get a product by ID",
            description = "Retrieve the product with the given ID"
    )
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @Operation(
            summary = "Get all products",
            description = "Retrieve a paginated list of all products"
    )
    @GetMapping
    public PagedProductResponse getAllProducts(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size
    ) {
        return productService.getAllProducts(page, size);
    }

    @Operation(
            summary = "Delete a product",
            description = "Delete the product with the given ID"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }

    @Operation(
            summary = "Get products by category",
            description = "Retrieve a list of products belonging to the specified category"
    )
    @GetMapping("/category/{categoryId}")
    public List<ProductResponse> getProductsByCategory(@PathVariable Integer categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

}
