package com.example.Filmorate12.storage;

import com.example.Filmorate12.exceptions.InvalidValidationException;
import com.example.Filmorate12.exceptions.NotFoundException;
import com.example.Filmorate12.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private Map<Integer, User> users = new HashMap<>();
    private int id = 0;

    @Override
    public User create(User user) {
        validate(user);
        user.setId(++id);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User upload(User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("Пользователь с id " + user.getId() + " не найден для обновления");
        }
        validate(user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Collection<User> get() {
        return users.values();
    }

    @Override
    public User getById(int id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("Пользователь с id " + id + " не найден");
        }
        return users.get(id);
    }

    public void validate(User user) {
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new InvalidValidationException("Электронная почта не может быть пустой и должна содержать символ @!");
        }

        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new InvalidValidationException("Логин не может быть пустым и содержать пробелы!");
        }

        if (user.getName().isBlank()) {
            user.getLogin();
        }

        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new InvalidValidationException("Дата рождения не может быть в будущем!");
        }
    }
}
