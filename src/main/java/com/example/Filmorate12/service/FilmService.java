package com.example.Filmorate12.service;

import com.example.Filmorate12.model.Film;
import com.example.Filmorate12.storage.FilmStorage;
import com.example.Filmorate12.storage.UserStorage;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addLike(int film_id, int user_id) {
        Film film = filmStorage.getById(film_id);
        userStorage.getById(user_id);
        film.getLikes().add(user_id);
    }

    public void removeLike(int film_id, int user_id) {
        Film film = filmStorage.getById(film_id);
        userStorage.getById(user_id);
        film.getLikes().remove(user_id);
    }

    public List<Film> getPopularFilms(int count) {
        return filmStorage.get().stream()
                .sorted((film1, film2) -> Integer.compare(film2.getLikes().size(), film1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film post(Film film) {
        return filmStorage.post(film);
    }

    public Film upload(Film film) {
        return filmStorage.upload(film);
    }

    public Collection<Film> get() {
        return filmStorage.get();
    }

    public Film getById(int id) {
        return filmStorage.getById(id);
    }

}
