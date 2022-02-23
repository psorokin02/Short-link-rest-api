package com.example.shortlinks.service.impl;

import com.example.shortlinks.exceptions.MyAuthenticationException;
import com.example.shortlinks.model.User;
import com.example.shortlinks.repository.UserRepository;
import com.example.shortlinks.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.init.UncategorizedScriptException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(User user) throws AuthenticationException {
        log.info("try to register user with username: {}, password = {}",
                user.getUsername(), user.getPassword());
        if(user.getUsername() == null || user.getUsername().isEmpty()){
            throw new MyAuthenticationException("username must be not empty");
        }
        if(user.getPassword() == null || user.getPassword().isEmpty()){
            throw new MyAuthenticationException("password must be not empty");
        }
        if(getByUsername(user.getUsername()) != null){
            throw new MyAuthenticationException("user with such username already exist");
        }
        if(getById(user.getId()) != null){
            throw new MyAuthenticationException("user with such id already exist");
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        log.info("getting all users");
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        log.info("getting user with id: {}", id);
        return userRepository.getById((int) id.longValue());
    }

    @Override
    public User getByUsername(String username) {
        log.info("getting user with username: {}", username);
        return userRepository.findByUsername(username);
    }
}
