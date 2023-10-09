package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final UserStorage storage;

    @Autowired
    public UserService(UserStorage storage) {
        this.storage = storage;
    }

    public User createUser(User user) {
        return storage.createUser(user);
    }

    public Collection<User> getUsers() {
        return storage.getUsers();
    }

    public User updateUser(User user) {
        return storage.updateUser(user);
    }

    public User getUserById(Long id) {
        return storage.getUserById(id);
    }

    public void addFriend(long userId, long friendId) {
        final User user = storage.getUserById(userId);
        final User friend = storage.getUserById(friendId);
        if (user == null || friend == null) {
            throw new NotFoundException("Пользователь не найден.");
        }
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    public void deleteFriend(long userId, long friendId) {
        final User user = storage.getUserById(userId);
        final User friend = storage.getUserById(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    public List<User> getFriends(Long id) {
        return storage.getFriends(id);
    }

    public List<User> getCommonFriends(long id, long otherId) {
        final User user = storage.getUserById(id);
        final User other = storage.getUserById(otherId);
        final Set<Long> friends = user.getFriends();
        final Set<Long> otherFriends = other.getFriends();

        return friends.stream()
                .filter(otherFriends::contains)
                .map(storage::getUserById)
                .collect(Collectors.toList());
    }

    public User deleteUser(Long id) {
        return storage.deleteUser(id);
    }

}
