package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Getter
@Builder
@EqualsAndHashCode(of = {"senderId", "recipientId"})
@AllArgsConstructor
@Setter
public class Friendship {
    private Long senderId;
    private Long recipientId;
    private boolean isFriend;
}
