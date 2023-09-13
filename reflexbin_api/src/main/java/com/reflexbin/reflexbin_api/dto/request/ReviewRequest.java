package com.reflexbin.reflexbin_api.dto.request;

import lombok.*;

import java.io.Serializable;

/**
 * Request class for Review
 */
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer rating;
    private String message;
}
