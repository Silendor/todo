package ru.coldwinternight.todo.exception;

public class TaskNotFoundException extends Exception {

    public TaskNotFoundException() {
        super("Task not found");
    }

    public TaskNotFoundException(String message) {
        super(message);
    }
}
