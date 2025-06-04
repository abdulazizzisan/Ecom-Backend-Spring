package com.kolu.ecombackend.review;

import com.kolu.ecombackend.auth.model.User;
import com.kolu.ecombackend.review.model.dto.ReviewRequest;
import com.kolu.ecombackend.review.model.dto.ReviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@Tag(name = "Review", description = "Review API")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{productId}")
    public ReviewResponse addReview(
            @PathVariable Integer productId,
            @RequestBody ReviewRequest request,
            @AuthenticationPrincipal User user
    ) {
        return reviewService.addReview(productId, request, user);
    }

    @Operation(summary = "Get all reviews of logged in user by product ID")
    @GetMapping("/user/{productId}")
    public ReviewResponse getReview(
            @PathVariable Integer productId,
            @AuthenticationPrincipal User user
    ) {
        return reviewService.getReview(productId, user);
    }

    @Operation(summary = "Get all reviews by product ID")
    @GetMapping("/{productId}")
    public List<ReviewResponse> getAllReviewsByProduct(
            @PathVariable Integer productId
    ) {
        return reviewService.getAllReviewsByProduct(productId);
    }

    @PutMapping("/{reviewId}")
    public ReviewResponse updateReview(
            @PathVariable Integer reviewId,
            @RequestBody ReviewRequest request,
            @AuthenticationPrincipal User user
    ) {
        return reviewService.updateReview(reviewId, request, user);
    }

}
