package com.reflexbin.reflexbin_api.dto.request;

import lombok.*;

/**
 * Request class for Review
 */
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    private Integer rating;
    private String message;
}
