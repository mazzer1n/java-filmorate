package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage storage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage storage, UserStorage userStorage) {
        this.storage = storage;
        this.userStorage = userStorage;
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

    public void addLike(long id, long userId) {
        final Film film = storage.getFilm(id);
        userStorage.getUserById(userId);
        film.addLike(userId);
    }

    public void removeLike(long id, long userId) {
        final Film film = storage.getFilm(id);
        userStorage.getUserById(userId);
        film.removeLike(userId);
    }

    public Collection<Film> getPopularFilms(int count) {
        return storage.getFilms()
                .stream()
                .sorted(Comparator.comparingInt(Film::getLikes).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film getFilm(Long id) {
        return storage.getFilm(id);
    }

    public Film deleteFilm(Long id) {
        return storage.deleteFilm(id);
    }
}
