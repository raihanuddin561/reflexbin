package com.reflexbin.reflexbin_api.service.impl;

import com.reflexbin.reflexbin_api.dto.request.CompanyCategoryRequest;
import com.reflexbin.reflexbin_api.dto.response.CompanyCategoryResponse;
import com.reflexbin.reflexbin_api.model.CompanyCategory;
import com.reflexbin.reflexbin_api.repository.CompanyCategoryRepository;
import com.reflexbin.reflexbin_api.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation class for CompanyService
 */
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyCategoryRepository companyCategoryRepository;

    /**
     * Method for creating new company category
     *
     * @param companyCategoryRequest
     */
    @Override
    public void createCompanyCategory(CompanyCategoryRequest companyCategoryRequest) {
        companyCategoryRepository.save(
                CompanyCategory.builder()
                        .categoryName(companyCategoryRequest.getCategoryName())
                        .build());
    }

    /**
     * Method for retrieving all company categories
     *
     * @return List<CompanyCategoryResponse>
     */
    @Override
    public List<CompanyCategoryResponse> getAllCompanyCategories() {
        List<CompanyCategory> categoryList = companyCategoryRepository.findAll();
        return categoryList.stream().map(category -> CompanyCategoryResponse.builder()
                .categoryId(category.getId())
                .categoryName(category.getCategoryName())
                .build()).collect(Collectors.toList());
    }
}
