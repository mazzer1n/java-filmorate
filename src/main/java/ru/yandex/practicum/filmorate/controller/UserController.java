package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int id = 1;

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        if (user.getName().equals("")) user.setName(user.getLogin());
        if (user.getLogin().contains(" ")) {
            log.warn("Неккоректный логин: {}", user.getLogin());
            throw new ValidationException("Некорректный логин.");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.debug("Неверно указано поле birthday - {}", user.getBirthday());
            throw new ValidationException("Некорректная  дата рождения.");
        }
        user.setId(id++);
        log.debug("Зарегистрирован новый пользователь: id - {}, name - {}, email - {} , login - {}, birthday - {}",
                user.getId(), user.getName(), user.getEmail(), user.getLogin(), user.getBirthday());
        users.put(user.getId(), user);
        return user;
    }

    @GetMapping("/users")
    public Collection<User> getUsers() {
        log.debug("Текущее колличество пользователей: {}", users.size());
        return users.values();
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        if (user.getName().isEmpty()) user.setName(user.getLogin());
        if (user.getLogin().contains(" ")) {
            log.warn("Неккоректный логин при обновлении: {}", user.getLogin());
            throw new ValidationException("Некорректный логин.");
        }
        User userOld = users.get(user.getId());
        if (userOld != null) {
            users.put(user.getId(), user);
            log.debug("Пользователь {} был успешно обновлен.", user.getId());
        } else {
            log.debug("Неккоректный id - {}", user.getId());
            throw new ValidationException("Неккоректный id.");
        }
        return user;
    }


}
