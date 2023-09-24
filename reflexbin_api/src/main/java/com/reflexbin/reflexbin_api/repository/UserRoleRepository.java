package com.reflexbin.reflexbin_api.repository;

import com.reflexbin.reflexbin_api.model.UserEntityRole;
import com.reflexbin.reflexbin_api.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserEntityRole,Long> {
    Optional<UserEntityRole> findByRole(String role);
}
