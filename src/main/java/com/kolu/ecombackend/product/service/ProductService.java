package com.kolu.ecombackend.product.service;

import com.kolu.ecombackend.category.service.CategoryService;
import com.kolu.ecombackend.product.model.Product;
import com.kolu.ecombackend.product.model.dto.PagedProductResponse;
import com.kolu.ecombackend.product.model.dto.ProductRequest;
import com.kolu.ecombackend.product.model.dto.ProductResponse;
import com.kolu.ecombackend.product.repository.ProductRepository;
import com.kolu.ecombackend.product.utils.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    public List<ProductResponse> getProductsByCategory(Integer categoryId) {
        return repository.findByCategoryId(categoryId)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    public ProductResponse saveProduct(ProductRequest request) {
        var product = productMapper
                .toEntity(
                        request,
                        categoryService
                                .getCategoryEntityById(request.categoryId())
                );
        return productMapper.toResponse(repository.save(product));
    }

    public ProductResponse getProductById(Integer id) {
        var product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toResponse(product);
    }

    public PagedProductResponse getAllProducts(int page, int size) {

        Pageable p = PageRequest.of(page, size);

        Page<Product> products = repository.findAll(p);

        List<ProductResponse> content = products.getContent()
                .stream()
                .map(productMapper::toResponse)
                .toList();

        return PagedProductResponse.builder()
                .content(content)
                .page(page)
                .size(size)
                .totalElements(products.getTotalElements())
                .totalPages(products.getTotalPages())
                .lastPage(products.isLast())
                .build();
    }

    public void deleteProduct(Integer id) {
        var product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        repository.delete(product);
    }

    public ProductResponse updateProduct(Integer id, ProductRequest request) {
        var product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productMapper.updateEntity(product, request);
        return productMapper.toResponse(repository.save(product));
    }

}
