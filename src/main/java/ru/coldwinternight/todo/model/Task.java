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
    private Integer userId;
    private String title;
    private String taskBody;
    private boolean completed;
    private boolean today;
    private boolean archived;

    public static Task toModel(TaskEntity entity) {
        return new Task(
                entity.getId(),
                entity.getUser().getId(),
                entity.getTitle(),
                entity.getTaskBody(),
                entity.isCompleted(),
                entity.isToday(),
                entity.isArchived()
        );
    }
}
