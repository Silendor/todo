package ru.coldwinternight.todo.exception;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException() {
        super("User already exist");
    }

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
