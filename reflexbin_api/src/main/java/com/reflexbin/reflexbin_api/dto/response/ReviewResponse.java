package com.reflexbin.reflexbin_api.dto.response;

import lombok.*;

import java.time.ZonedDateTime;

/**
 * Response class for Review
 */
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private Integer rating;
    private String message;
    private ZonedDateTime createdAt;
}
