package com.reflexbin.reflexbin_api.repository;

import com.reflexbin.reflexbin_api.model.CompanyCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for CompanyCategory
 */
public interface CompanyCategoryRepository extends JpaRepository<CompanyCategory, Long> {
}
