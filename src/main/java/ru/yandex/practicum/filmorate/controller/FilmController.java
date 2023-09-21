package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
@Slf4j
@RestController
public class FilmController {
    private final HashMap<String, Film> films = new HashMap<>();
    private int id = 1;

    @PostMapping("/films")
    public void createFilm(@Valid @RequestBody Film film) {

    }

    @GetMapping("/films")
    public Collection<Film> getFilm() {
        log.debug("Текущее колличество фильмов: {}", films.size());
        return films.values();
    }

    @PutMapping("/films")
    public void updateUser(@Valid @RequestBody User user) {
        if (user.getName().isEmpty()) user.setName(user.getLogin());
        if (user.getLogin().contains(" ")) {
            log.warn("Неккоректный логин при обновлении: {}", user.getLogin());
            throw new ValidationException("Некорректный логин.");
        }
        users.put(user.getEmail(), user);
    }

}
