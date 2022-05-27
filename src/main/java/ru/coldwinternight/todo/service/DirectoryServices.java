package ru.coldwinternight.todo.service;

import ru.coldwinternight.todo.entity.DirectoryEntity;

import java.util.List;

public interface DirectoryServices {
    void create(DirectoryEntity directory);

    List<DirectoryEntity> readAllForUser(int userId);

    DirectoryEntity read(int id);

    boolean update(DirectoryEntity directory, int id);

    boolean delete(int id);
}