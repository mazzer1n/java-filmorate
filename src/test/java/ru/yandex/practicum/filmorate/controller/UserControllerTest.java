package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserControllerTest {

    private final InMemoryUserStorage storage = new InMemoryUserStorage();
    private final User user = new User();

    @BeforeEach
    public void beforeEach() {
        user.setName("User Name");
        user.setLogin("userLogin");
        user.setBirthday(LocalDate.now().minusYears(5));
        user.setEmail("yandex@mail.ru");
    }

    @Test
    void validateUserOk() {
        storage.validateUser(user);
    }

    @Test
    void validateFilmFail() {
        user.setLogin("User Login");
        Exception exception = assertThrows(ValidationException.class, () -> storage.validateUser(user));
        assertEquals("Некорректный логин.", exception.getMessage());

        user.setLogin("UserLogin");
        user.setBirthday(LocalDate.now().plusDays(1));
        exception = assertThrows(ValidationException.class, () -> storage.validateUser(user));
        assertEquals("Некорректная  дата рождения.", exception.getMessage());

    }
}