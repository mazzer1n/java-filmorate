package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Long, Film> films = new HashMap<>();
    private Long id = 0L;

    public Film createFilm(Film film) {
        validateFilm(film);
        film.setId(++id);
        films.put(film.getId(), film);
        log.info("Фильм <{}> успешно добавлен", film.getName());
        return film;

    }

    public Collection<Film> getFilms() {
        log.info("Текущее колличество фильмов: {}", films.size());
        return films.values();
    }

    public Film getFilm(Long id) {
        if (films.get(id) == null) {
            throw new NotFoundException("Фильм не найден.");
        }
        return films.get(id);
    }

    public Film updateFilm(Film film) {
        validateFilm(film);
        Film filmOld = films.get(film.getId());
        if (filmOld != null) {
            films.put(film.getId(), film);
            log.info("Фильм <{}> успешно обновлен", film.getId());
        } else {
            throw new NotFoundException("Неверный идентификатор.");
        }
        return film;
    }

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

    public Film changeLike(final Long filmId, final Long userId, boolean addition) {
        Film film = films.get(filmId);
        if (film == null) {
            throw new NotFoundException("Фильм не найден.");
        }
        if (userId < 1) {
            throw new NotFoundException("Неверный id.");
        }
        if (addition) {
            film.putLike(userId);
        } else {
            film.removeLike(userId);
        }
        return film;
    }

    public Collection<Film> getPopularFilms(int count) {
        return films.values()
                .stream()
                .sorted(Comparator.comparingInt(Film::getLikes).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}
