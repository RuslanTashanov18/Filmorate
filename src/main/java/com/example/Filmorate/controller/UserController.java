package com.example.Filmorate.controller;

import com.example.Filmorate.exceptions.InvalidValidationException;
import com.example.Filmorate.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
public class UserController {
    private Map<Integer, User> users = new HashMap<>();
    private int id = 0;
    @PostMapping("/users")
    public User create(@RequestBody User user) {
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.error("Электронная почта: {}", user.getEmail());
            throw new InvalidValidationException("Электронная почта не может быть пустой и должна содержать символ @!");
        }

        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.error("Логин: {}", user.getLogin());
            throw new InvalidValidationException("Логин не может быть пустым и содержать пробелы!");
        }

        if (user.getName().isBlank()) {
            log.error("Имя: {}", user.getName());
            user.getLogin();
        }

        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Дата рождения: {}", user.getBirthday());
            throw new InvalidValidationException("Дата рождения не может быть в будущем!");
        }
        user.setId(id);
        users.put(user.getId(), user);
        log.info("Создан пользователь: {}", user);
        return user;
    }
    @PutMapping("/users")
    public User upload(@RequestBody User user) {
        users.remove(0);
        users.put(user.getId(), user);
        log.info("Обновлен пользователь: {}", user);

        return user;
    }
    @GetMapping("/users")
    public Collection<User> get() {
        log.info("Текущее количество пользователей: {}", users.size());
        return users.values();
    }
}
