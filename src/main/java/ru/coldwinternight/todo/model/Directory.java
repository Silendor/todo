package ru.coldwinternight.todo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.coldwinternight.todo.entity.DirectoryEntity;

@Getter
@Setter
@AllArgsConstructor
public class Directory {
    private Integer id;
    private String name;

    public static Directory toModel(DirectoryEntity entity) {
        return new Directory(
                entity.getId(),
                entity.getName()
        );
    }
}