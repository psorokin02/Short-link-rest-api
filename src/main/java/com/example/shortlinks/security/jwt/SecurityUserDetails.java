package com.example.shortlinks.security.jwt;

import com.example.shortlinks.model.Role;
import com.example.shortlinks.model.User;
import com.example.shortlinks.model.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityUserDetails implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private Date creationDate;
    private UserStatus status;
    private List<Role> roles;
    private List<? extends GrantedAuthority> authorities;

    public static SecurityUserDetails createFromUser(User user){
        return new SecurityUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getCreationDate(),
                user.getStatus(),
                user.getRoles());
    }

    private SecurityUserDetails(Long id, String username, String password, Date creationDate,
                                UserStatus status, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.creationDate = creationDate;
        this.status = status;
        this.roles = roles;
        this.authorities = roles.stream().map(
                role -> new SimpleGrantedAuthority(role.getRole())
        ).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status.getStatus().equals("ACTIVE");
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.getStatus().equals("ACTIVE");
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status.getStatus().equals("ACTIVE");
    }

    @Override
    public boolean isEnabled() {
        return status.getStatus().equals("ACTIVE");
    }
}
