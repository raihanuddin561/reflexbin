package com.reflexbin.reflexbin_api.service.impl;

import com.reflexbin.reflexbin_api.dto.request.ReviewRequest;
import com.reflexbin.reflexbin_api.dto.response.ReviewResponse;
import com.reflexbin.reflexbin_api.model.Review;
import com.reflexbin.reflexbin_api.repository.ReviewRepository;
import com.reflexbin.reflexbin_api.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Implementation class for ReviewService
 */
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    /**
     * Method for saving reviewRequest
     *
     * @param reviewRequest
     * @return ReviewResponse
     */
    @Override
    public void saveReview(ReviewRequest reviewRequest) {
        Review review = Review.builder()
                .userId(1L)//custom for now
                .rating(reviewRequest.getRating())
                .message(reviewRequest.getMessage())
                .createdAt(ZonedDateTime.now())
                .build();
        reviewRepository.save(review);
    }

    /**
     * Method for retrieving review by reviewId
     *
     * @param reviewId
     * @return ReviewResponse
     */
    @Override
    public ReviewResponse getReviewByReviewId(Long reviewId) {
        // todo: need to work on exception
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();
            return ReviewResponse.builder()
                    .reviewId(review.getId())
                    .rating(review.getRating())
                    .message(review.getMessage())
                    .createdAt(review.getCreatedAt())
                    .build();
        } else {
            return null;
        }
    }
}
