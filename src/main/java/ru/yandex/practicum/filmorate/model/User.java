package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDate;

@Data
public class User {
    private int id = 0;
    @Email
    private final String email;
    @NotBlank
    private final String login;
    private String name = "";
    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birthday;

}
