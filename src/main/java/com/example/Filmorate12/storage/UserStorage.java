package com.example.Filmorate12.storage;

import com.example.Filmorate12.model.User;

import java.util.Collection;

public interface UserStorage {
    User create(User user);
    User upload(User user);
    Collection<User> get();
    User getById(int id);
}