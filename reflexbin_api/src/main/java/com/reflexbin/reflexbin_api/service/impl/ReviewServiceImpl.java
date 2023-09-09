package com.reflexbin.reflexbin_api.service.impl;

import com.reflexbin.reflexbin_api.dto.request.ReviewRequest;
import com.reflexbin.reflexbin_api.dto.response.ReviewResponse;
import com.reflexbin.reflexbin_api.model.Review;
import com.reflexbin.reflexbin_api.repository.ReviewRepository;
import com.reflexbin.reflexbin_api.service.ReviewService;
import com.reflexbin.reflexbin_api.service.converter.ReviewConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation class for ReviewService
 */
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewConverter reviewConverter;

    /**
     * Method for saving reviewRequest
     *
     * @param reviewRequest
     * @return ReviewResponse
     */
    @Override
    public void saveReview(ReviewRequest reviewRequest) {
        reviewRepository.save(reviewConverter.requestToEntity(reviewRequest));
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
            return reviewConverter.entityToResponse(reviewOptional.get());
        } else {
            return null;
        }
    }
}
