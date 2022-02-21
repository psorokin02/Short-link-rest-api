package com.example.shortlinks.dto;

public class AuthenticationRequestDto {
    private Long id;
    private String Password;

    public AuthenticationRequestDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
