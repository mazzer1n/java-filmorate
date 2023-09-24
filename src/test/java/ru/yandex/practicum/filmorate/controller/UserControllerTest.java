package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserControllerTest {

    private final UserController userController = new UserController();
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
        userController.validateUser(user);
    }

    @Test
    void validateFilmFail() {
        user.setLogin("User Login");
        Exception exception = assertThrows(ValidationException.class, () -> userController.validateUser(user));
        assertEquals("Некорректный логин.", exception.getMessage());

        user.setLogin("UserLogin");
        user.setBirthday(LocalDate.now().plusDays(1));
        exception = assertThrows(ValidationException.class, () -> userController.validateUser(user));
        assertEquals("Некорректная  дата рождения.", exception.getMessage());

    }
}