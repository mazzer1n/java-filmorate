package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Long, User> users = new HashMap<>();
    private Long id = 0L;

    public User createUser(User user) {
        validateUser(user);
        if (user.getName().equals("")) user.setName(user.getLogin());
        user.setId(++id);
        log.info("Зарегистрирован новый пользователь: id - {}, name - {}, email - {} , login - {}, birthday - {}",
                user.getId(), user.getName(), user.getEmail(), user.getLogin(), user.getBirthday());
        users.put(user.getId(), user);
        return user;
    }

    public Collection<User> getUsers() {
        log.info("Текущее колличество пользователей: {}", users.size());
        return users.values();
    }

    public User updateUser(User user) {
        validateUser(user);
        User userOld = users.get(user.getId());
        if (userOld != null) {
            users.put(user.getId(), user);
            log.info("Пользователь {} был успешно обновлен.", user.getId());
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

    public void changeFriend(Long firstId, Long secondId, boolean addition) {
        User first = users.get(firstId);
        User second = users.get(secondId);
        if (first == null || second == null) {
            throw new NotFoundException("Пользователь не найден.");
        }
        if (addition) {
            first.addFriend(secondId);
            second.addFriend(firstId);
        } else {
            first.deleteFriend(secondId);
            second.deleteFriend(firstId);
        }
    }

    public List<User> getFriends(Long id) {
        User user = users.get(id);
        if (user == null) {
            throw new NotFoundException("Пользователь не найден.");
        }
        final List<User> friends = new ArrayList<>();
        for (Long friendId : user.getFriends()) {
            friends.add(users.get(friendId));
        }
        return friends;
    }

    public List<User> getCommonFriends(Long firstId, Long secondId) {
        User first = users.get(firstId);
        User second = users.get(secondId);
        if (first == null || second == null) {
            throw new NotFoundException("Пользователь не найден.");
        }
        Set<Long> commonFriendsId = first.getCommonFriends(second);
        List<User> commonFriends = new ArrayList<>();
        for (Long id : commonFriendsId) {
            User user = users.get(id);
            commonFriends.add(user);
        }
        return commonFriends;
    }

}
