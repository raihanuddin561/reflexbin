package com.reflexbin.reflexbin_api.dto.request;

import lombok.*;

import java.io.Serializable;

/**
 * Request class for CompanyCategory
 */
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyCategoryRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String categoryName;
}
