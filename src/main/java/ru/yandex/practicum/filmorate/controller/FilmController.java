package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
public class FilmController {
    private final HashMap<String, Film> films = new HashMap<>();
    private int id = 1;

    @PostMapping("/films")
    public Film createFilm(@Valid @RequestBody Film film) {
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Описание содержит более 200 символов.");
        }
        LocalDate minReleaseDate = LocalDate.of(1895, 12, 28);
        if (film.getReleaseDate().isBefore(minReleaseDate)) {
            throw new ValidationException("Неверная дата релиза.");
        }
        film.setId(id++);
        films.put(film.getName(), film);
        log.debug("Фильм <{}> успешно добавлен", film.getName());
        return film;

    }

    @GetMapping("/films")
    public Collection<Film> getFilm() {
        log.debug("Текущее колличество фильмов: {}", films.size());
        return films.values();
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Описание содержит более 200 символов.");
        }
        LocalDate minReleaseDate = LocalDate.of(1895, 12, 28);
        if (film.getReleaseDate().isBefore(minReleaseDate)) {
            throw new ValidationException("Неверная дата релиза.");
        }
        Film filmOld = films.get(film.getName());
        if (filmOld == null) {
            film.setId(id++);
            films.put(film.getName(), film);
            log.debug("Фильм <{}> успешно добавлен", film.getName());
        } else {
            film.setId(filmOld.getId());
            films.put(film.getName(), film);
            log.debug("Фильм <{}> успешно обновлен", film.getName());
        }
        return film;
    }

}
