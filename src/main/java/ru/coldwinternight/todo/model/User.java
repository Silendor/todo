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
    private List<Note> notes;
    private List<Directory> directories;

    public static User toModel(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getNotes().stream().map(Note::toModel).collect(Collectors.toList()),
                entity.getDirectories().stream().map(Directory::toModel).collect(Collectors.toList())
        );
    }
}
