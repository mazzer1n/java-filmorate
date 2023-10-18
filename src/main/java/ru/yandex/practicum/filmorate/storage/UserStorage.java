package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    public User createUser(User user);

    public Collection<User> getUsers();

    public User updateUser(User user);

    public User getUserById(Long id);

    void deleteUser(Long id);

}
