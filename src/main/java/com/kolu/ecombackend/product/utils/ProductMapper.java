package com.kolu.ecombackend.product.utils;

import com.kolu.ecombackend.category.model.Category;
import com.kolu.ecombackend.product.model.Product;
import com.kolu.ecombackend.product.model.dto.ProductRequest;
import com.kolu.ecombackend.product.model.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    public ProductResponse toResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .categoryId(product.getCategory().getId())
                .build();
    }

    public Product toEntity(ProductRequest productRequest, Category category) {
        return Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .stockQuantity(productRequest.stockQuantity())
                .category(category)
                .build();
    }

    public void updateEntity(Product product, ProductRequest productRequest) {
        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setPrice(productRequest.price());
        product.setStockQuantity(productRequest.stockQuantity());
    }

}
