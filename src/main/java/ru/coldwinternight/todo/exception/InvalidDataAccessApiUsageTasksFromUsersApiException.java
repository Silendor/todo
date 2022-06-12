package ru.coldwinternight.todo.exception;

import org.springframework.dao.InvalidDataAccessApiUsageException;

public class InvalidDataAccessApiUsageTasksFromUsersApiException extends InvalidDataAccessApiUsageException {

    public InvalidDataAccessApiUsageTasksFromUsersApiException() {
        super("You can't edit tasks from users api");
    }

    public InvalidDataAccessApiUsageTasksFromUsersApiException(String msg) {
        super(msg);
    }

    public InvalidDataAccessApiUsageTasksFromUsersApiException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
