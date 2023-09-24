package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.group.Marker;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@Validated
@RestController
public class UserController {
    private final HashMap<Long, User> users = new HashMap<>();
    private Long id = 1L;

    @PostMapping("/users")
    @Validated(Marker.OnCreate.class)
    public User createUser(@Valid @RequestBody User user) {
        validateUser(user);
        if (user.getName().equals("")) user.setName(user.getLogin());
        user.setId(id++);
        log.info("Зарегистрирован новый пользователь: id - {}, name - {}, email - {} , login - {}, birthday - {}",
                user.getId(), user.getName(), user.getEmail(), user.getLogin(), user.getBirthday());
        users.put(user.getId(), user);
        return user;
    }

    @GetMapping("/users")
    public Collection<User> getUsers() {
        log.info("Текущее колличество пользователей: {}", users.size());
        return users.values();
    }

    @PutMapping("/users")
    @Validated(Marker.OnUpdate.class)
    public User updateUser(@Valid @RequestBody User user) {
        validateUser(user);
        User userOld = users.get(user.getId());
        if (userOld != null) {
            users.put(user.getId(), user);
            log.info("Пользователь {} был успешно обновлен.", user.getId());
        } else {
            throw new ValidationException("Неккоректный id.");
        }
        return user;
    }

    public void validateUser(User user) {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Некорректный логин.");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Некорректная  дата рождения.");
        }

    }


}
