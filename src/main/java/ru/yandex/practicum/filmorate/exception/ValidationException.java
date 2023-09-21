package ru.yandex.practicum.filmorate.exception;

public class ValidationException extends IllegalArgumentException {
    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }

}
