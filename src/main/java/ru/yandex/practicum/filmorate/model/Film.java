package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@ToString
public class Film {
    @Positive
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

    private Set<Long> likesId;
    private Set<Genre> genres;
    private MpaRating mpa;

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("name", name);
        values.put("description", description);
        values.put("release_date", releaseDate);
        values.put("duration", duration);
        values.put("rating_id", mpa.getId());

        return values;
    }
}

