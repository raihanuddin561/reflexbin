package com.reflexbin.reflexbin_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Entity class for CompanyCategory
 */
@Entity
@Table(name = "_company_category")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String categoryName;
}
