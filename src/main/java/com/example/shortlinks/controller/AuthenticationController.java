package com.example.shortlinks.controller;

import com.example.shortlinks.security.token.TokenProvider;
import com.example.shortlinks.security.token.UserToken;
import com.example.shortlinks.security.user.User;
import com.example.shortlinks.security.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthenticationController {
    private UserService userService;
    private TokenProvider tokenProvider;

    public AuthenticationController(UserService userService, TokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(HttpServletRequest request, @RequestBody User user){
        if(!userService.authenticate(user)){
            return new ResponseEntity<>("Wrong id or secret", HttpStatus.FORBIDDEN);
        }
        String token = tokenProvider.createToken(user, request);
        UserToken userToken = new UserToken(user.getId(), token);
        userService.saveUserToken(userToken);
        return new ResponseEntity<>("token:" + token, HttpStatus.OK);
    }
}
