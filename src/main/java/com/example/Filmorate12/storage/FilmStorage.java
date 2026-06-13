package com.example.Filmorate12.storage;

import com.example.Filmorate12.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film post(Film film);
    Film upload(Film film);
    Collection<Film> get();
    Film getById(int id);
}
