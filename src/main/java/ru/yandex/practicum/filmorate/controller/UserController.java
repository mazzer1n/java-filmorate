package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@Validated
@RestController
@Slf4j
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        User addUser = service.createUser(user);
        log.info("Зарегистрирован новый пользователь: id - {}, name - {}, email - {} , login - {}, birthday - {}",
                addUser.getId(), addUser.getName(), addUser.getEmail(), addUser.getLogin(), addUser.getBirthday());
        return addUser;
    }

    @GetMapping("/users")
    public Collection<User> getUsers() {
        log.info("Текущее колличество пользователей: {}", service.getUsers().size());
        return service.getUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        User updateUser = service.updateUser(user);
        log.info("Пользователь {} был успешно обновлен.", updateUser.getId());
        return updateUser;
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        service.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        service.deleteFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public Collection<User> getFriends(@PathVariable Long id) {
        return service.getFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return service.getCommonFriends(id, otherId);
    }

    @DeleteMapping("/users/{id}")
    public boolean deleteUser(@PathVariable Long id) {
        boolean result = service.deleteUser(id);
        log.info("Пользователь {} был успешно удален.", id);
        return result;
    }

}
