package com.reflexbin.reflexbin_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "_user_profile")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_holder")
    private boolean jobHolder;

    @Column(name = "company_name")
    private String companyName;

    private String designation;

    @Column(name = "years_of_xp")
    private Integer yearsOfExperience;
}
