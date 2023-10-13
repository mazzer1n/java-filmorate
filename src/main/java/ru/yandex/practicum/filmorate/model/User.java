package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yandex.practicum.filmorate.group.Marker;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    @Null(groups = Marker.OnCreate.class)
    @NotNull(groups = Marker.OnUpdate.class)
    private Long id;
    @Email
    private String email;
    @NotBlank
    private String login;
    private String name = "";
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate birthday;
    private final Set<Long> friends = new HashSet<>();

    public void addFriend(Long id) {
        friends.add(id);
    }

    public void deleteFriend(Long id) {
        friends.remove(id);
    }

    public Set<Long> getCommonFriends(User user) {
        Set<Long> duplicates = new HashSet<>(friends);
        duplicates.retainAll(user.getFriends());
        return duplicates;
    }

}
