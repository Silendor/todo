package ru.coldwinternight.todo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.coldwinternight.todo.entity.NoteEntity;

@Getter
@Setter
@AllArgsConstructor
public class Note {
    private Integer id;
    private Integer user_id;
//    private String userName;
    private Integer directory_id; // can be null
//    private String directoryName; // can be null
    private String note;
    private String title;
    private boolean completed;

    public static Note toModel(NoteEntity entity) {
        return new Note(
                entity.getId(),
                entity.getUser().getId(),
                entity.getDirectory() == null ? null : entity.getDirectory().getId(),
                entity.getNote(),
                entity.getTitle(),
                entity.isCompleted()
        );
    }
}
