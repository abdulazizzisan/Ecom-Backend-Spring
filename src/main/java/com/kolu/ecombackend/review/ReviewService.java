package com.kolu.ecombackend.review;

import com.kolu.ecombackend.auth.model.User;
import com.kolu.ecombackend.product.model.Product;
import com.kolu.ecombackend.product.repository.ProductRepository;
import com.kolu.ecombackend.review.model.Review;
import com.kolu.ecombackend.review.model.dto.ReviewRequest;
import com.kolu.ecombackend.review.model.dto.ReviewResponse;
import com.kolu.ecombackend.review.utils.ReviewMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository repository;
    private final ProductRepository productRepository;
    private final ReviewMappers mappers;

    public List<ReviewResponse> getAllReviewsByProduct(Integer productId){
        return repository.findAllByProductId(productId)
                .stream()
                .map(mappers::toResponse)
                .toList();
    }

    public ReviewResponse addReview(Integer productId, ReviewRequest request, User user){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Review newReview = mappers.toEntity(request, product, user);
        return mappers.toResponse(repository.save(newReview));
    }

    public ReviewResponse getReview(Integer productId, User user) {
        Review review = repository.findByProductIdAndUserId(productId, user.getId());
        return mappers.toResponse(review);
    }

    public ReviewResponse updateReview(Integer reviewId, ReviewRequest request, User user) {
        Review review = repository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        if (!review.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to update this review");
        }
        return mappers.toResponse(repository.save(mappers.toUpdateEntity(review, request)));
    }
}
