package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public interface UserStorage {
    public User createUser(User user);

    public Collection<User> getUsers();

    public User updateUser(User user);

    public void validateUser(User user);

    public User getUserById(Long id);

    public List<User> getFriends(Long id);

    User deleteUser(Long id);

}
