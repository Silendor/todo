package ru.coldwinternight.todo.exception;

public class EmailCannotBeNullException extends Exception {
    public EmailCannotBeNullException() {
        super("Email cannot be null");
    }

    public EmailCannotBeNullException(String message) {
        super(message);
    }
}
