package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@Component
@Slf4j
@Qualifier("inMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Long, User> users = new HashMap<>();
    private Long id = 0L;

    public User createUser(User user) {
        validateUser(user);
        if (user.getName().equals("")) user.setName(user.getLogin());
        user.setId(++id);
        users.put(user.getId(), user);
        return user;
    }

    public Collection<User> getUsers() {
        return users.values();
    }

    public User updateUser(User user) {
        validateUser(user);
        User userOld = users.get(user.getId());
        if (userOld != null) {
            users.put(user.getId(), user);
        } else {
            throw new NotFoundException("Пользователь не найден.");
        }
        return user;
    }

    public User getUserById(Long id) {
        final User user = users.get(id);
        if (user != null) {
            return user;
        } else {
            throw new NotFoundException("Пользователь не найден.");
        }
    }

    public void validateUser(User user) {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Некорректный логин.");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Некорректная  дата рождения.");
        }
    }

    public void deleteUser(Long id) {
        User user = users.remove(id);
        if (user == null) {
            throw new NotFoundException("Пользователь не найден.");
        }
    }

}
