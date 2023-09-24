package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.swing.table.DefaultTableCellRenderer;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    private final FilmController filmController = new FilmController();
    private final Film film = new Film();

    @BeforeEach
    public void beforeEach() {
        film.setName("Film Name");
        film.setDescription("Film description");
        film.setReleaseDate(LocalDate.now());
        film.setDuration(100);
    }

    @Test
    void validateFilmOk() {
        filmController.validateFilm(film);
    }

    @Test
    void validateFilmFail() {
        film.setName("");
        Exception exception = assertThrows(ValidationException.class, () -> filmController.validateFilm(film));
        assertEquals("Неккоректное название фильма.", exception.getMessage());

        film.setName("film Name");
        film.setDescription("f".repeat(201));
        exception = assertThrows(ValidationException.class, () -> filmController.validateFilm(film));
        assertEquals("Описание содержит более 200 символов.", exception.getMessage());

        film.setDescription("Film description");
        film.setReleaseDate(LocalDate.MIN);
        exception = assertThrows(ValidationException.class, () -> filmController.validateFilm(film));
        assertEquals("Неверная дата релиза.", exception.getMessage());

        film.setReleaseDate(LocalDate.now());
        film.setDuration(-10);
        exception = assertThrows(ValidationException.class, () -> filmController.validateFilm(film));
        assertEquals("Некорректная продолжительность.", exception.getMessage());
    }
}