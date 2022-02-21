package com.example.shortlinks.security.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private Long id;
    private String secret;
    private final List<SimpleGrantedAuthority> authorities;

    public MyUserDetails(Long id, String secret, List<SimpleGrantedAuthority> authorities) {
        this.id = id;
        this.secret = secret;
        this.authorities = authorities;
    }

    public MyUserDetails(User user) {
        this.id = user.getId();
        this.secret = user.getSecret();
        this.authorities = List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return secret;
    }

    @Override
    public String getUsername() {
        return id.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }
}
