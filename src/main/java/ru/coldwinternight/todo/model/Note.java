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
    private Integer userId;
    private String userName;
    private Integer directoryId; // can be null
    private String directoryName; // can be null
    private String note;
    private String title;
    private boolean completed;

    // default constructor
    public Note(Integer id, Integer userId, String userName, String note, String title, boolean completed) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.note = note;
        this.title = title;
        this.completed = completed;
    }

    public static Note toModel(NoteEntity entity) {
        return new Note(
                entity.getId(),
                entity.getUser().getId(),
                entity.getUser().getUsername(),
                entity.getNote(),
                entity.getTitle(),
                entity.isCompleted()
        );
    }

    public static Note toModelWithDirectory(NoteEntity entity) {
        return new Note(
                entity.getId(),
                entity.getUser().getId(),
                entity.getUser().getUsername(),
                entity.getDirectory().getId(),
                entity.getDirectory().getName(),
                entity.getNote(),
                entity.getTitle(),
                entity.isCompleted()
        );
    }
}
