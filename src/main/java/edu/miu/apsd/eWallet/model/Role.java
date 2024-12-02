package edu.miu.apsd.eWallet.model;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {

    CUSTOMER(Set.of(Permission.LIST_PRODUCT, Permission.READ_PRODUCT)),

    MERCHANT(Set.of(Permission.LIST_PRODUCT, Permission.CREATE_PRODUCT, Permission.READ_PRODUCT));

    private final Set<Permission> permissions;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        // Add Role to authorities
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        // Add Permission to authorities
        authorities.addAll(permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList()));

        return authorities;
    }
}
