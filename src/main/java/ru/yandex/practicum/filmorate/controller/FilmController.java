package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.group.Marker;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@Slf4j
@Validated
@RestController
public class FilmController {

    private final FilmService service;

    @Autowired
    public FilmController(FilmService service) {
        this.service = service;
    }


    @PostMapping("/films")
    @Validated(Marker.OnCreate.class)
    public Film createFilm(@Valid @RequestBody Film film) {
        Film newFilm = service.createFilm(film);
        log.info("Фильм <{}> успешно добавлен", film.getName());
        return newFilm;

    }

    @GetMapping("/films")
    public Collection<Film> getFilm() {
        return service.getFilms();
    }

    @GetMapping("/films/{id}")
    public Film getFilm(@PathVariable Long id) {
        return service.getFilm(id);
    }

    @PutMapping("/films")
    @Validated(Marker.OnCreate.class)
    public Film updateFilm(@Valid @RequestBody Film film) {
        return service.updateFilm(film);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void putLike(@PathVariable Long id, @PathVariable Long userId) {
        service.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void removeLike(@PathVariable Long id, @PathVariable Long userId) {
        service.removeLike(id, userId);
    }

    @GetMapping("/films/popular")
    public Collection<Film> getPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        return service.getPopularFilms(count);
    }

    @DeleteMapping("/films/{id}")
    public Film deleteFilm(@PathVariable Long id) {
        return service.deleteFilm(id);
    }

}
