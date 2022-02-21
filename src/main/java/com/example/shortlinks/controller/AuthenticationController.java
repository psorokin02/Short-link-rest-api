package com.example.shortlinks.controller;

import com.example.shortlinks.security.token.TokenProvider;
import com.example.shortlinks.security.token.UserToken;
import com.example.shortlinks.security.user.User;
import com.example.shortlinks.security.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private UserService userService;
    private TokenProvider tokenProvider;

    public AuthenticationController(AuthenticationManager authenticationManager, UserService userService, TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(HttpServletRequest request, @RequestBody User user){
        try {
            System.out.println("try to auth");
            System.out.println("User: " + user.getId() + " " + user.getSecret());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getId().toString(), user.getSecret()));
            System.out.println("authed");
            UserToken token = tokenProvider.createToken(user, request);
            userService.saveUserToken(token);
            return new ResponseEntity<>("token: " + token, HttpStatus.OK);
        }
        catch (
        AuthenticationException e) {
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(path = "/")
    public String test(){
        return "test success";
    }
}
