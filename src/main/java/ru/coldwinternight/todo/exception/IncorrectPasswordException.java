package ru.coldwinternight.todo.exception;

public class IncorrectPasswordException extends Exception{
    public IncorrectPasswordException() {
        super("Incorrect password.");
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }
}
