package com.reflexbin.reflexbin_api.service.converter;

import com.reflexbin.reflexbin_api.dto.request.ReviewRequest;
import com.reflexbin.reflexbin_api.dto.response.ReviewResponse;
import com.reflexbin.reflexbin_api.model.Review;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

/**
 * dto mapper/converter class for Review
 */
@Service
public class ReviewConverter {
    /**
     * method for converting ReviewRequest to Review
     *
     * @param reviewRequest
     * @return Review
     */
    public Review requestToEntity(ReviewRequest reviewRequest) {
        return Review.builder()
                .userId(1L)//custom for now
                .rating(reviewRequest.getRating())
                .message(reviewRequest.getMessage())
                .createdAt(ZonedDateTime.now())
                .build();
    }

    /**
     * method for converting Review to ReviewResponse
     *
     * @param review
     * @return ReviewResponse
     */
    public ReviewResponse entityToResponse(Review review) {
        return ReviewResponse.builder()
                .reviewId(review.getId())
                .rating(review.getRating())
                .message(review.getMessage())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
