package ru.yandex.practicum.filmorate.storage;


import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

@Repository
public interface FilmStorage {

    public Film createFilm(Film film);

    public Collection<Film> getFilms();

    public Film updateFilm(Film film);

    public void validateFilm(Film film);

    public Film getFilm(Long id);

    Film deleteFilm(Long id);
}
