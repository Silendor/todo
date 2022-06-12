package ru.coldwinternight.todo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.coldwinternight.todo.entity.TaskEntity;

@Getter
@Setter
@AllArgsConstructor
public class Task {
    private Integer id;
    private Integer user_id;
//    private String userName;
    private String task_body;
    private String title;
    private boolean completed;
    private boolean today;

    public static Task toModel(TaskEntity entity) {
        return new Task(
                entity.getId(),
                entity.getUser().getId(),
                entity.getTask_body(),
                entity.getTitle(),
                entity.isCompleted(),
                entity.isToday()
        );
    }
}
