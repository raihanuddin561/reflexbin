package com.reflexbin.reflexbin_api.model;

import com.reflexbin.reflexbin_api.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "_user_role")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntityRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;
    @ManyToMany(mappedBy = "userRole",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<UserEntity> userEntities;
}
