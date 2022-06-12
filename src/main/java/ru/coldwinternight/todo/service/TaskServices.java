package ru.coldwinternight.todo.service;

import ru.coldwinternight.todo.entity.TaskEntity;
import ru.coldwinternight.todo.exception.TaskNotFoundException;
import ru.coldwinternight.todo.exception.UserNotFoundException;
import ru.coldwinternight.todo.model.Task;

import java.util.List;

public interface TaskServices {
    void create(TaskEntity note, int userId) throws UserNotFoundException;

    List<Task> readAllByUserId(int userId) throws UserNotFoundException, TaskNotFoundException;

    void reverseCompletedStatus(int id) throws TaskNotFoundException;

    Task read(int id) throws TaskNotFoundException;

    void update(TaskEntity note, int id) throws TaskNotFoundException;

    void delete(int id) throws TaskNotFoundException;
}