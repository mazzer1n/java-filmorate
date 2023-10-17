package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Long, Film> films = new HashMap<>();
    private Long id = 0L;

    public Film createFilm(Film film) {
        film.setId(++id);
        films.put(film.getId(), film);
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

    @Override
    public Film addLike(Long filmId, Long userId) {
        Film film = this.getFilm(filmId);
        film.getLikesId().add(userId);

        return film;
    }

    @Override
    public Film removeLike(Long filmId, Long userId) {
        Film film = this.getFilm(filmId);

        if (userId <= 0) {
            throw new NotFoundException("Лайк данного пользователя не найден.");
        }


        if (film.getLikesId().contains(userId)) {
            film.getLikesId().remove(userId);
        } else {
            throw new NotFoundException("Лайк данного пользователя не найден.");
        }

        return film;
    }

    public boolean deleteFilm(Long id) {
        return films.remove(id) != null;
    }

    public Film updateFilm(Film film) {
        Film filmOld = films.get(film.getId());
        if (filmOld != null) {
            films.put(film.getId(), film);
            log.info("Фильм <{}> успешно обновлен", film.getId());
        } else {
            throw new NotFoundException("Неверный идентификатор.");
        }
        return film;
    }


}
