package com.example.shortlinks.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public class UserAuthenticationException extends AuthenticationException{

    private HttpStatus httpStatus;

    public UserAuthenticationException(String msg) {
        super(msg);
    }
    public UserAuthenticationException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
