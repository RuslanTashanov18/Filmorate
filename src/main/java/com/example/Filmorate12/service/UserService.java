package com.example.Filmorate12.service;

import com.example.Filmorate12.model.User;
import com.example.Filmorate12.storage.InMemoryUserStorage;
import com.example.Filmorate12.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addToFriends(int user_id, int friend_id) {
        User user = userStorage.getById(user_id);
        User friend = userStorage.getById(friend_id);
        user.getFriends().add(friend_id);
        friend.getFriends().add(friend_id);
    }

    public void removeFriend(int user_id, int friend_id) {
        User user = userStorage.getById(user_id);
        User friend = userStorage.getById(friend_id);
        user.getFriends().remove(friend_id);
        friend.getFriends().remove(user_id);
    }

    public List<User> getFriends(int user_id) {
        User user = userStorage.getById(user_id);
        return user.getFriends().stream()
                .map(userStorage::getById)
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(int user_id, int friend_id) {
        User user = userStorage.getById(user_id);
        User friend = userStorage.getById(friend_id);
        Set<Integer> userFriends = user.getFriends();
        Set<Integer> friendFriends = friend.getFriends();
        return userFriends.stream()
                .filter(friendFriends::contains)
                .map(userStorage::getById)
                .collect(Collectors.toList());
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User upload(User user) {
        return userStorage.upload(user);
    }

    public Collection<User> get() {
        return userStorage.get();
    }

    public User getById(int id) {
        return userStorage.getById(id);
    }

}

