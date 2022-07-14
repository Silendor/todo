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
    private String taskBody;
    private String title;
    private boolean completed;
    private boolean today;
    private boolean archived;

    public static Task toModel(TaskEntity entity) {
        return new Task(
                entity.getId(),
                entity.getUser().getId(),
                entity.getTaskBody(),
                entity.getTitle(),
                entity.isCompleted(),
                entity.isToday(),
                entity.isArchived()
        );
    }
}
