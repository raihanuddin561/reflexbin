package com.reflexbin.reflexbin_api.controller;

import com.reflexbin.reflexbin_api.constant.APIEndpoints;
import com.reflexbin.reflexbin_api.constant.ApplicationConstants;
import com.reflexbin.reflexbin_api.constant.enums.ResponseType;
import com.reflexbin.reflexbin_api.dto.BaseResponse;
import com.reflexbin.reflexbin_api.dto.request.ReviewRequest;
import com.reflexbin.reflexbin_api.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * RestController class for review
 */
@RestController
@RequestMapping(value = APIEndpoints.REVIEW)
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * Add review
     *
     * @param reviewRequest
     * @return BaseResponse
     */
    @PostMapping(APIEndpoints.REVIEW_ADD)
    public BaseResponse addReview(@RequestBody ReviewRequest reviewRequest) {
        reviewService.saveReview(reviewRequest);
        return BaseResponse.builder()
                .responseType(ResponseType.SUCCESS)
                .message(Collections.singleton(ApplicationConstants.CREATED_MSG))
                .code(ApplicationConstants.SUCCESS_CODE)
                .build();
    }
}
