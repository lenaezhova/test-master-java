package com.testmasterapi.domain.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;

@Getter
public class CustomUserDetails extends User {
    private final Long id;
    private final Set<UserRoles> roles;

    public CustomUserDetails(
            com.testmaster.model.User user,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super( user.getEmail(), user.getPassword(), authorities );
        this.id = user.getId();
        this.roles = user.getRoles();
    }
}