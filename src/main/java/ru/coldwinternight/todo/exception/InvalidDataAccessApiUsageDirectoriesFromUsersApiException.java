package ru.coldwinternight.todo.exception;

import org.springframework.dao.InvalidDataAccessApiUsageException;

public class InvalidDataAccessApiUsageDirectoriesFromUsersApiException extends InvalidDataAccessApiUsageException {

    public InvalidDataAccessApiUsageDirectoriesFromUsersApiException() {
        super("You can't edit directories from users api");
    }

    public InvalidDataAccessApiUsageDirectoriesFromUsersApiException(String msg) {
        super(msg);
    }

    public InvalidDataAccessApiUsageDirectoriesFromUsersApiException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
