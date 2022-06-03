package ru.coldwinternight.todo.service;

import ru.coldwinternight.todo.entity.DirectoryEntity;
import ru.coldwinternight.todo.exception.DirectoryNotFoundException;
import ru.coldwinternight.todo.exception.UserNotFoundException;
import ru.coldwinternight.todo.model.Directory;

import java.util.List;

public interface DirectoryServices {
    void create(DirectoryEntity directory, int userId) throws UserNotFoundException;

    List<Directory> readAllByUserId(int userId) throws UserNotFoundException, DirectoryNotFoundException;

    Directory read(int id) throws DirectoryNotFoundException;

    void update(DirectoryEntity directory, int id) throws DirectoryNotFoundException;

    void delete(int id) throws DirectoryNotFoundException;
}