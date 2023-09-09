package com.reflexbin.reflexbin_api.controller;

import com.reflexbin.reflexbin_api.constant.APIEndpoints;
import com.reflexbin.reflexbin_api.constant.ApplicationConstants;
import com.reflexbin.reflexbin_api.constant.enums.ResponseType;
import com.reflexbin.reflexbin_api.dto.BaseResponse;
import com.reflexbin.reflexbin_api.dto.request.CompanyCategoryRequest;
import com.reflexbin.reflexbin_api.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * RestController class for Company and CompanyCategory
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(APIEndpoints.COMPANY)
public class CompanyController {
    private final CompanyService companyService;

    /**
     * Create company category
     *
     * @param companyCategoryRequest
     * @return
     */
    @PostMapping(APIEndpoints.COMPANY_CATEGORY_CREATE)
    public BaseResponse createCompanyCategory(@RequestBody CompanyCategoryRequest companyCategoryRequest) {
        companyService.createCompanyCategory(companyCategoryRequest);
        return BaseResponse.builder()
                .responseType(ResponseType.RESULT)
                .message(Collections.singleton(ApplicationConstants.CREATED_MSG))
                .code(ApplicationConstants.SUCCESS_CODE)
                .build();
    }

    /**
     * Get all company categories
     *
     * @return
     */
    @GetMapping(APIEndpoints.COMPANY_CATEGORY_GET_ALL)
    public BaseResponse getAllCompanyCategories() {
        return BaseResponse.builder()
                .responseType(ResponseType.RESULT)
                .result(companyService.getAllCompanyCategories())
                .code(ApplicationConstants.SUCCESS_CODE)
                .build();
    }
}
