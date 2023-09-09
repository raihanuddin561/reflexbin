package com.reflexbin.reflexbin_api.service;

import com.reflexbin.reflexbin_api.dto.request.ReviewRequest;
import com.reflexbin.reflexbin_api.dto.response.ReviewResponse;

/**
 * ReviewService interface
 */
public interface ReviewService {

    void saveReview(ReviewRequest reviewRequest);

    ReviewResponse getReviewByReviewId(Long reviewId);
}
