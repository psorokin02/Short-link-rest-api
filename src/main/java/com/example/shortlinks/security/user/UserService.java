package com.example.shortlinks.security.user;

import com.example.shortlinks.security.token.TokenRepository;
import com.example.shortlinks.security.token.UserToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private TokenRepository tokenRepository;

    public UserService(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    public User getUserById(Long id){
        return userRepository.getById(id);
    }

    public UserToken getUserTokenById(Long id){
        return tokenRepository.getById(id);
    }

    public void saveUserToken(UserToken token){
        tokenRepository.save(token);
    }

//    public boolean checkUserToken(Long id, String token){
//        UserToken userToken = getUserTokenById(id);
//        return userToken != null && userToken.getToken().equals(token);
//    }
}
