package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
public class UserController {
    private final HashMap<String, User> users = new HashMap<>();
    private int id = 1;

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        if (user.getName().equals("")) user.setName(user.getLogin());
        if (user.getLogin().contains(" ")) {
            log.warn("Неккоректный логин: {}", user.getLogin());
            throw new ValidationException("Некорректный логин.");
        }
        if (users.containsKey(user.getEmail())) {
            log.warn("Пользователь c email - {} уже существует", user.getEmail());
            throw new ValidationException("Такой пользователь уже существует.");
        }
        user.setId(++id);
        log.debug("Зарегистрирован новый пользователь: id - {}, name - {}, email - {} , login - {}, birthday - {}",
                user.getId(), user.getName(), user.getEmail(), user.getLogin(), user.getBirthday());
        users.put(user.getEmail(), user);
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
        User userOld = users.get(user.getEmail());
        if (userOld != null) {
            user.setId(userOld.getId());
            log.debug("Пользователь {} был успешно обновлен.", user.getEmail());
        } else {
            user.setId(++id);
            log.debug("Зарегистрирован новый пользователь: id - {}, name - {}, email - {} , login - {}, birthday - {}",
                    user.getId(), user.getName(), user.getEmail(), user.getLogin(), user.getBirthday());
        }
        users.put(user.getEmail(), user);
        return user;
    }


}
