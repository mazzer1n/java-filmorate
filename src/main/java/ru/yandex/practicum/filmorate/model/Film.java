package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yandex.practicum.filmorate.group.Marker;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    @Null(groups = Marker.OnCreate.class)
    @NotNull(groups = Marker.OnUpdate.class)
    private Long id;
    @NotBlank
    private String name;
    @Size(min = 1, max = 200)
    private String description;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    @Min(1)
    private long duration;
    private final Set<Long> likesId = new HashSet<>();

    public void addLike(Long userId) {
        likesId.add(userId);
    }

    public void removeLike(Long userId) {
        likesId.remove(userId);
    }

    public int getLikes() {
        return likesId.size();
    }
}
