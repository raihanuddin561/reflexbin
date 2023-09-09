package com.reflexbin.reflexbin_api.service;

import com.reflexbin.reflexbin_api.dto.request.CompanyCategoryRequest;
import com.reflexbin.reflexbin_api.dto.response.CompanyCategoryResponse;

import java.util.List;

/**
 * CompanyService interface
 */
public interface CompanyService {
    void createCompanyCategory(CompanyCategoryRequest companyCategoryRequest);

    List<CompanyCategoryResponse> getAllCompanyCategories();
}
