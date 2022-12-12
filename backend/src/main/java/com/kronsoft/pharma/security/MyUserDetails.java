package com.kronsoft.pharma.security;

import com.kronsoft.pharma.auth.AuthToken;
import com.kronsoft.pharma.user.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {
    private final AppUser user;
    private final Collection<? extends GrantedAuthority> authorities;
    private AuthToken activeAuthToken;

    public MyUserDetails(AppUser user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public static MyUserDetails build(AppUser user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new MyUserDetails(
                user,
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public AppUser getUser() {
        return user;
    }

    public AuthToken getActiveAuthToken() {
        return activeAuthToken;
    }

    public void setActiveAuthToken(AuthToken authToken) {
        activeAuthToken = authToken;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.getIsBanned() && user.getIsEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getIsEnabled();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MyUserDetails userDetails = (MyUserDetails) o;
        return Objects.equals(user.getId(), userDetails.getUser().getId());
    }
}
