package com.reflexbin.reflexbin_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@Table(name = "authorities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorityEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String authority;
    @ManyToMany(mappedBy = "authorities")
    private Collection<RoleEntity> roles;
}
