package com.reflexbin.reflexbin_api.dto.response;

import lombok.*;

import java.io.Serializable;

/**
 * Response class for CompanyCategory
 */
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyCategoryResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long categoryId;
    private String categoryName;
}
