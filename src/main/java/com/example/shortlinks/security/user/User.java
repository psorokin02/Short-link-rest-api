package com.example.shortlinks.security.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "secret")
    private String secret;

    public User() {
    }

    public User(Long id, String secret) {
        this.id = id;
        this.secret = secret;
    }


    public User(MyUserDetails userDetails){
        this.id = userDetails.getId();
        this.secret = userDetails.getPassword();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
