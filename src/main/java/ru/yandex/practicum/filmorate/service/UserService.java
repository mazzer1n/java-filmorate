package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.List;

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

    public void addFriend(Long firstId, Long secondId) {
        storage.changeFriend(firstId, secondId, true);
    }

    public void deleteFriend(Long firstId, Long secondId) {
        storage.changeFriend(firstId, secondId, false);
    }

    public List<User> getFriends(Long id) {
        return storage.getFriends(id);
    }

    public List<User> getCommonFriends(Long firstId, Long secondId) {
        return storage.getCommonFriends(firstId, secondId);
    }


}
