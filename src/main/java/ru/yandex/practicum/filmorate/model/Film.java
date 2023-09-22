package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDate;

@Data
public class Film {
    private int id = 0;
    @NotBlank
    private final String name;
    @NotBlank
    private final String description;
    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate releaseDate;
    @Positive
    private final int duration;
}
