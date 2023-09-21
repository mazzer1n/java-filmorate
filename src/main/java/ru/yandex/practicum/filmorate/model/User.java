package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NonNull;

import java.time.Instant;

@Data
public class User {
    private int id;
    @Email
    private final String email;
    @NotBlank
    private final String login;
    private String name = "";
    @PastOrPresent
    private final Instant birthday;

}
