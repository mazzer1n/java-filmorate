package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.EqualsAndHashCode;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
public class Genre {
    private Integer id;
    private String name;
}