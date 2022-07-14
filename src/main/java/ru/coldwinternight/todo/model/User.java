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
    private Integer todayAmount;
    private boolean randomizeTodayTasks;
    private List<Task> tasks;

    public static User toModel(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getTodayAmount(),
                entity.isRandomizeTodayTasks(),
                entity.getTasks().stream().map(Task::toModel).collect(Collectors.toList())
        );
    }
}
