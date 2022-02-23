package com.example.shortlinks.security;

import com.example.shortlinks.model.User;
import com.example.shortlinks.security.jwt.SecurityUserDetails;
import com.example.shortlinks.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SecurityUserDetailsService implements UserDetailsService {

    private UserService userService;

    public SecurityUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUsername(username);
        if(user == null){
            return null;
        }
        log.info("got user with username: {}", username);
        return SecurityUserDetails.createFromUser(user);
    }
}
