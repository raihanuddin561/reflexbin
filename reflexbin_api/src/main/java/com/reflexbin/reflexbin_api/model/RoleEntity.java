package com.reflexbin.reflexbin_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@Table(name = "roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String role;
    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> userEntities;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "roles_authorities",
            joinColumns = @JoinColumn(
                    name = "roles_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "authorities_id",
                    referencedColumnName = "id"
            )
    )
    private Collection<AuthorityEntity> authorities;
}
