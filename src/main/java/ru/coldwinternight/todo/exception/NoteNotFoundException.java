package ru.coldwinternight.todo.exception;

public class NoteNotFoundException extends Exception {

    public NoteNotFoundException() {
        super("Note not found");
    }

    public NoteNotFoundException(String message) {
        super(message);
    }
}
