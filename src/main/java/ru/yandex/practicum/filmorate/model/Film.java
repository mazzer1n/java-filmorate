package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.Instant;

@Data
public class Film {
    private int id;
    @NotBlank
    private final String name;
    @NotBlank
    private final String description;
    @PastOrPresent
    private final Instant releaseDate;
    @Positive
    private final int duration;
}
