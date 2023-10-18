package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
public class User {
    @Positive
    private Long id;

    @Email
    @NotNull
    private String email;

    @NotBlank
    @NotNull
    private String login;
    private String name = "";

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate birthday;

    private Set<Long> friends;
    private List<Friendship> friendships;

    public Map<String, Object> toMap() {
        if (name == null || name.isEmpty()) {
            name = login;
        }

        Map<String, Object> values = new HashMap<>();
        values.put("email", email);
        values.put("login", login);
        values.put("name", name);
        values.put("birthday", birthday);

        return values;
    }
}

