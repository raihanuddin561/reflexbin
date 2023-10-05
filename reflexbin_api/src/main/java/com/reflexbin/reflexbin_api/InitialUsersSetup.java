package com.reflexbin.reflexbin_api;

import com.reflexbin.reflexbin_api.model.AuthorityEntity;
import com.reflexbin.reflexbin_api.model.RoleEntity;
import com.reflexbin.reflexbin_api.model.UserEntity;
import com.reflexbin.reflexbin_api.model.enums.Role;
import com.reflexbin.reflexbin_api.repository.AuthorityRepository;
import com.reflexbin.reflexbin_api.repository.RoleRepository;
import com.reflexbin.reflexbin_api.repository.UserRepository;
import com.reflexbin.reflexbin_api.utils.DateUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class InitialUsersSetup {
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("From Application Ready Event");
        AuthorityEntity readAuthority = createAuthority("READ");
        AuthorityEntity writeAuthority = createAuthority("WRITE");
        AuthorityEntity deleteAuthority = createAuthority("DELETE");

        createRole(Role.ROLE_USER.name(), Arrays.asList(readAuthority, writeAuthority));
        RoleEntity roleAdmin = createRole(Role.ROLE_ADMIN.name(), Arrays.asList(readAuthority, writeAuthority, deleteAuthority));
        if (roleAdmin == null) return;
        UserEntity adminUser = UserEntity.builder()
                .firstName("Raihan")
                .lastName("Uddin")
                .email("raihan@gmail.com")
                .password(passwordEncoder.encode("raihan"))
                .active(true)
                .createdAt(DateUtils.getCurrentDateTime())
                .lastUpdatedAt(DateUtils.getCurrentDateTime())
                .roles(List.of(roleAdmin))
                .build();
        UserEntity admin = userRepository.findByEmail(adminUser.getEmail()).orElse(
                null
        );
        if (admin == null) {
            admin = userRepository.save(adminUser);
        }
    }

    @Transactional
    private RoleEntity createRole(String name, Collection<AuthorityEntity> authorityEntities) {
        RoleEntity roleEntity = roleRepository.findByRole(name).orElse(null);
        if (roleEntity != null) {
            return roleEntity;
        }
        roleEntity = RoleEntity.builder()
                .role(name)
                .authorities(authorityEntities)
                .build();
        return roleRepository.save(roleEntity);
    }

    @Transactional
    private AuthorityEntity createAuthority(String name) {
        AuthorityEntity authorityEntity = authorityRepository.findByAuthority(name).orElse(
                null
        );
        if (authorityEntity != null) return authorityEntity;
        return authorityRepository.save(AuthorityEntity.builder()
                .authority(name)
                .build());
    }
}
