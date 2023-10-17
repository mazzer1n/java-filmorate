package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@Component
public class FilmValidator {

    public void validateFilm(Film film) {
        if (film.getName().equals("")) throw new ValidationException("Неккоректное название фильма.");
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Описание содержит более 200 символов.");
        }
        LocalDate minReleaseDate = LocalDate.of(1895, 12, 28);
        if (film.getReleaseDate().isBefore(minReleaseDate)) {
            throw new ValidationException("Неверная дата релиза.");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Некорректная продолжительность.");
        }
    }
}
