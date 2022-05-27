package ru.coldwinternight.todo.service;

import ru.coldwinternight.todo.entity.NoteEntity;
import ru.coldwinternight.todo.exception.NoteNotFoundException;
import ru.coldwinternight.todo.exception.UserNotFoundException;
import ru.coldwinternight.todo.model.Note;

import java.util.List;

public interface NoteServices {
    void create(NoteEntity note, int userId) throws UserNotFoundException;

    List<Note> readAllByUser(int userId) throws NoteNotFoundException, UserNotFoundException;

    void reverseCompletedStatus(int id) throws NoteNotFoundException;

    Note read(int id) throws NoteNotFoundException;

    void update(NoteEntity note, int id) throws NoteNotFoundException;

    void delete(int id) throws NoteNotFoundException;
}