package com.reflexbin.reflexbin_api.dto.response;

import lombok.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Response class for Review
 */
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long reviewId;
    private Integer rating;
    private String message;
    private ZonedDateTime createdAt;
}
