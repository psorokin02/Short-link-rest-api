package com.example.shortlinks.security.token;

import com.example.shortlinks.hashcode.URLHashCoder;
import org.springframework.stereotype.Component;

@Component
public class TokenHashCoder {

    private URLHashCoder hashCoder;

    public TokenHashCoder(URLHashCoder hashCoder) {
        this.hashCoder = hashCoder;
    }

    public String createToken(String data){
        return hashCoder.encodeURL(data);
    }


    public String decodeToken(String token){
        return hashCoder.decodeURL(token);
    }
}
