package com.reflexbin.reflexbin_api.dto.response;

import lombok.*;

/**
 * Response class for CompanyCategory
 */
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyCategoryResponse {
    private Long categoryId;
    private String categoryName;
}
