package ru.coldwinternight.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.coldwinternight.todo.entity.DirectoryEntity;
import ru.coldwinternight.todo.repository.DirectoryRepository;

import java.util.List;

@Service
public class DirectoryService implements DirectoryServices {

    private DirectoryRepository directoryRepository;

    @Autowired
    public DirectoryService(DirectoryRepository directoryRepository) {
        this.directoryRepository = directoryRepository;
    }

    @Override
    public void create(DirectoryEntity directory) {
        directoryRepository.save(directory);
    }

    @Override
    public List<DirectoryEntity> readAllForUser(int userId) {
        // не реализовано
        return null;
    }

    @Override
    public DirectoryEntity read(int id) {
        return directoryRepository.getById(id);
    }

    @Override
    public boolean update(DirectoryEntity directory, int id) {
        if (directoryRepository.existsById(id)) {
            directory.setId(id);
            directoryRepository.save(directory);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if (directoryRepository.existsById(id)) {
            directoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
