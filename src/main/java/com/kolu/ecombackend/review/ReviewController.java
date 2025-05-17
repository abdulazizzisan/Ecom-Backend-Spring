package com.kolu.ecombackend.review;

import com.kolu.ecombackend.auth.model.User;
import com.kolu.ecombackend.review.model.dto.ReviewRequest;
import com.kolu.ecombackend.review.model.dto.ReviewResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
}
