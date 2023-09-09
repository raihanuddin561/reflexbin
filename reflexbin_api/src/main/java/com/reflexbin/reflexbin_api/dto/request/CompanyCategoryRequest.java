package com.reflexbin.reflexbin_api.dto.request;

import lombok.*;

/**
 * Request class for CompanyCategory
 */
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyCategoryRequest {
    private String categoryName;
}
