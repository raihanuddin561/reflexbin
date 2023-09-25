package com.reflexbin.reflexbin_api.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import java.time.ZonedDateTime;
import java.util.Collection;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "users_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "roles_id",
                    referencedColumnName = "id"
            )
    )
    private Collection<RoleEntity> roles;

    @OneToOne
    private UserProfile userProfile;

    private boolean active;

    @Column(name = "created_at")
    @Generated(event = EventType.INSERT)
    private ZonedDateTime createdAt;

    @Column(name = "last_updated_at")
    private ZonedDateTime lastUpdatedAt;

}
