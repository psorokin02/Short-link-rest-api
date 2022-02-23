package com.example.shortlinks.service;

import com.example.shortlinks.model.User;

import java.util.List;

public interface UserService {
    public User register(User user);
    public List<User> getAll();
    public User getById(Long id);
    public User getByUsername(String username);
}
