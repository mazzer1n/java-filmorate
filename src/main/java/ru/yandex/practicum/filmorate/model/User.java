package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
