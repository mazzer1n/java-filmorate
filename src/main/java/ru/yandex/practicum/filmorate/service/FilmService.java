package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final FilmValidator validator;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage, FilmValidator validator) {
        this.filmStorage = filmStorage;
        this.validator = validator;
    }

    public Film createFilm(Film film) {
        validator.validateFilm(film);
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        validator.validateFilm(film);
        return filmStorage.updateFilm(film);
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilm(Long id) {
        return filmStorage.getFilm(id);
    }

    public void deleteFilm(Long id) {
        filmStorage.deleteFilm(id);
    }

    public Film addLike(Long filmId, Long userId) {
        return filmStorage.addLike(filmId, userId);
    }

    public Film removeLike(Long filmId, Long userId) {
        return filmStorage.removeLike(filmId, userId);
    }

    public List<Film> getPopularFilms(Integer count) {
        count = checkPopularFilmCount(count);

        filmStorage.getFilms().forEach(this::initLikesIfNull);

        return filmStorage.getFilms().stream()
                .sorted((film1, film2) -> film2.getLikesId().size() - film1.getLikesId().size())
                .limit(count)
                .collect(Collectors.toList());
    }

    public void initLikesIfNull(Film film) {
        Set<Long> likes = film.getLikesId();
        if (likes == null) {
            likes = new HashSet<>();
        }

        film.setLikesId(likes);
    }

    private Integer checkPopularFilmCount(Integer count) {
        if (count == null || count == 0) {
            count = 10;
        }

        return count;
    }
}