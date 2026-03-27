package com.example.Filmorate.controller;

import com.example.Filmorate.exceptions.InvalidValidationException;
import com.example.Filmorate.model.Film;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
public class FilmController {
    private Map<Integer,Film> films = new HashMap<>();
    private int id = 0;
    LocalDate date = LocalDate.of(1895, 12,28);
    @PostMapping("/films")
    public Film post(@RequestBody Film film) {
        if (film.getName().isBlank()) {
            log.error("Название фильма: {}", film.getName());
            throw new InvalidValidationException("Название фильма не может быть пустым!");
        }

        if (film.getDescription().length() >= 200) {
            log.error("Длина описания: {}", film.getDescription());
            throw new InvalidValidationException("Максимальная длина описания — 200 символов!");
        }

        if (film.getReleaseDate().isBefore(date)) {
            log.error("Дата релиза: {}", film.getReleaseDate());
            throw new InvalidValidationException("Дата релиза — не раньше 28 декабря 1895 года!");
        }

        if (film.getDuration().isNegative()) {
            log.error("Продолжительность фильма: {}", film.getDuration());
            throw new InvalidValidationException("Продолжительность фильма должна быть положительной!");
        }
        film.setId(++id);
        films.put(film.getId(), film);
        log.info("Добавлен фильм: {}", film);
        return film;
    }
    @PutMapping("/films")
    public Film upload(@RequestBody Film film) {
        if (films.isEmpty()) {
            throw new RuntimeException("Список фильмов пуст!");
        }
        films.remove(0);
        films.put(film.getId(), film);
        log.info("Обновление фильма: {}", film);
        return film;
    }
    @GetMapping("/films")
    public Collection<Film> get() {
        log.info("Текущее количество фильмов: {}", films.size());
        return films.values();
    }
}
