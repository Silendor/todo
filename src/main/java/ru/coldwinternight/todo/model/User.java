package ru.coldwinternight.todo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.coldwinternight.todo.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String email;
    private Integer today_amount;
    private boolean randomize_today_tasks;
    private List<Task> tasks;

    public static User toModel(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getToday_amount(),
                entity.isRandomize_today_tasks(),
                entity.getTasks().stream().map(Task::toModel).collect(Collectors.toList())
        );
    }
}
