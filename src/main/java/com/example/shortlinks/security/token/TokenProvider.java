package com.example.shortlinks.security.token;

import com.example.shortlinks.security.UserAuthenticationException;
import com.example.shortlinks.security.user.MyUserDetails;
import com.example.shortlinks.security.user.User;
import com.example.shortlinks.security.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TokenProvider {
    private UserService userService;

    public TokenProvider(UserService userService) {
        this.userService = userService;
    }

    public UserToken createToken(User user, HttpServletRequest request){
        StringBuilder builder = new StringBuilder();
        Map<String, String[]> params = request.getParameterMap();

        System.out.println(params.toString());//

        params.forEach((key, valuesArray) -> {
            Arrays.stream(valuesArray).forEach(
                    value -> builder.append(key).append("=").append(value).append("&")
            );
        });
        builder.append("secret").append("=").append(user.getSecret());

        System.out.println("params = " + builder);

        return new UserToken(user.getId(), new BCryptPasswordEncoder().encode(builder.toString()));
    }

    public boolean validateToken(UserToken token){
        UserToken userToken = userService.getUserTokenById(token.getId());
        if(userToken == null){
            throw new UserAuthenticationException("Authentication failed", HttpStatus.UNAUTHORIZED);
        }
        return token.getToken().equals(token.getToken());
    }

    public UserToken resolveToken(HttpServletRequest request) {
        if(request.getParameter("token") == null || request.getParameter("id") == null){
            return null;
        }
        String token =  request.getParameter("token");
        Long id = Long.valueOf(request.getParameter("id"));
        return new UserToken(id,token);
    }

    public Authentication getAuthentication(UserToken token) throws Exception {
        User user= userService.getUserById(token.getId());
        if(user == null){
            throw new Exception("wtf");
        }
        UserDetails userDetails = new MyUserDetails(user);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
