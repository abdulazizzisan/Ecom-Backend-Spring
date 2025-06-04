package com.kolu.ecombackend.review.utils;

import com.kolu.ecombackend.auth.model.User;
import com.kolu.ecombackend.product.model.Product;
import com.kolu.ecombackend.review.model.Review;
import com.kolu.ecombackend.review.model.dto.ReviewRequest;
import com.kolu.ecombackend.review.model.dto.ReviewResponse;
import org.springframework.stereotype.Component;

@Component
public class ReviewMappers {
    public ReviewResponse toResponse(Review review){
        return ReviewResponse.builder()
                .id(review.getId())
                .review(review.getUserReview())
                .rating(review.getRating())
                .build();
    }

    public Review toEntity(ReviewRequest request, Product product, User user){

        return Review.builder()
                .userReview(request.review())
                .rating(request.rating())
                .product(product)
                .user(user)
                .build();
    }

    public Review toUpdateEntity(Review review, ReviewRequest request){
        review.setUserReview(request.review());
        review.setRating(request.rating());
        return review;
    }
}
