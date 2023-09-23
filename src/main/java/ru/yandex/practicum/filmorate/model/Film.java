package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class Film {
    private int id = 0;
    @NotBlank
    private final String name;
    @NotBlank
    private final String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate releaseDate;
    @Positive // это аннотация, как и @min(value=0) - не работает
    private final int duration;
}
