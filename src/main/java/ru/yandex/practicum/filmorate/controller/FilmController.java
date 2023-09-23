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
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int id = 1;

    @PostMapping("/films")
    public Film createFilm(@Valid @RequestBody Film film) {
        if (film.getName().equals("")) throw new ValidationException("Неккоректное название фильма.");
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Описание содержит более 200 символов.");
        }
        LocalDate minReleaseDate = LocalDate.of(1895, 12, 28);
        if (film.getReleaseDate().isBefore(minReleaseDate)) {
            throw new ValidationException("Неверная дата релиза.");
        }
        if (film.getDuration() <= 0) {
            log.debug("Неверное значение для поля duration - {}", film.getDuration());
            throw new ValidationException("Некорректная продолжительность.");
        }
        film.setId(id++);
        films.put(film.getId(), film);
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
        if (film.getDuration() <= 0) {
            log.debug("Неверное значение для поля duration - {}", film.getDuration());
            throw new ValidationException("Некорректная продолжительность.");
        }
        Film filmOld = films.get(film.getId());
        if (filmOld != null) {
            films.put(film.getId(), film);
            log.debug("Фильм <{}> успешно обновлен", film.getId());
        } else {
            log.debug("Неверный id - {}", film.getId());
            throw new ValidationException("Неверный идентификатор.");
        }
        return film;
    }

}
