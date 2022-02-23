package com.example.shortlinks.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public class MyAuthenticationException extends AuthenticationException {
    private final HttpStatus httpStatus;

    public MyAuthenticationException(String msg) {
        super(msg);
        httpStatus = HttpStatus.valueOf(400);
    }

    public MyAuthenticationException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }
}