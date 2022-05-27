package ru.coldwinternight.todo.exception;

import org.springframework.dao.InvalidDataAccessApiUsageException;

public class InvalidDataAccessApiUsageNotesFromUsersApiException extends InvalidDataAccessApiUsageException {

    public InvalidDataAccessApiUsageNotesFromUsersApiException() {
        super("You can't edit notes from users api");
    }

    public InvalidDataAccessApiUsageNotesFromUsersApiException(String msg) {
        super(msg);
    }

    public InvalidDataAccessApiUsageNotesFromUsersApiException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
