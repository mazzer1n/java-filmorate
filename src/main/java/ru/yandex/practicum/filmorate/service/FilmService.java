package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;

@Service
public class FilmService {
    private final FilmStorage storage;

    @Autowired
    public FilmService(FilmStorage storage) {
        this.storage = storage;
    }

    public Film createFilm(Film film) {
        return storage.createFilm(film);
    }

    public Collection<Film> getFilms() {
        return storage.getFilms();
    }

    public Film updateFilm(Film film) {
        return storage.updateFilm(film);
    }

    public Film putLike(Long filmId, Long userId) {
        return storage.changeLike(filmId, userId, true);
    }

    public Film removeLike(Long filmId, Long userId) {
        return storage.changeLike(filmId, userId, false);
    }

    public Collection<Film> getPopularFilms(Integer count) {
        return storage.getPopularFilms(count);
    }

    public Film getFilm(Long id) {
        return storage.getFilm(id);
    }

}
