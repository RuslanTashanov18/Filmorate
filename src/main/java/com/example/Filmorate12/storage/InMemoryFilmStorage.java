package com.example.Filmorate12.storage;

import com.example.Filmorate12.exceptions.InvalidValidationException;
import com.example.Filmorate12.exceptions.NotFoundException;
import com.example.Filmorate12.model.Film;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Component
public class InMemoryFilmStorage implements FilmStorage {

    private Map<Integer, Film> films = new HashMap<>();
    private int id = 0;
    LocalDate date = LocalDate.of(1895, 12,28);

    @Override
    public Film post(Film film) {
        validate(film);
        film.setId(++id);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film upload(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Фильм с id " + film.getId() + " не найден для обновления");
        }
        validate(film);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Collection<Film> get() {
        return films.values();
    }

    @Override
    public Film getById(int id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException("Фильм с id " + id + " не найден");
        }
        return films.get(id);
    }

    public void validate(Film film) {
        if (film.getName().isBlank()) {
            throw new InvalidValidationException("Название фильма не может быть пустым!");
        }

        if (film.getDescription().length() >= 200) {
            throw new InvalidValidationException("Максимальная длина описания — 200 символов!");
        }

        if (film.getReleaseDate().isBefore(date)) {
            throw new InvalidValidationException("Дата релиза — не раньше 28 декабря 1895 года!");
        }
    }
}
