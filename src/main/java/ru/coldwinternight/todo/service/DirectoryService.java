package ru.coldwinternight.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.coldwinternight.todo.entity.DirectoryEntity;
import ru.coldwinternight.todo.entity.UserEntity;
import ru.coldwinternight.todo.exception.DirectoryNotFoundException;
import ru.coldwinternight.todo.exception.UserNotFoundException;
import ru.coldwinternight.todo.model.Directory;
import ru.coldwinternight.todo.repository.DirectoryRepository;
import ru.coldwinternight.todo.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DirectoryService implements DirectoryServices {

    private final DirectoryRepository directoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public DirectoryService(DirectoryRepository directoryRepository, UserRepository userRepository) {
        this.directoryRepository = directoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void create(DirectoryEntity directory, int userId) throws UserNotFoundException {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        directory.setUser(user);
        directoryRepository.save(directory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Directory> readAllByUserId(int userId) throws UserNotFoundException, DirectoryNotFoundException {
        // не реализовано
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return directoryRepository.findAllByUser_Id(userId).stream().map(Directory::toModel).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Directory read(int id) throws DirectoryNotFoundException {
        DirectoryEntity directory = directoryRepository.findById(id)
                .orElseThrow(DirectoryNotFoundException::new);
        return Directory.toModel(directory);
    }

    @Override
    @Transactional
    public void update(DirectoryEntity directory, int id) throws DirectoryNotFoundException {
        directoryRepository.findById(id)
                .orElseThrow(DirectoryNotFoundException::new);
        directory.setId(id);
        directoryRepository.save(directory);
    }

    @Override
    @Transactional
    public void delete(int id) throws DirectoryNotFoundException {
        directoryRepository.findById(id)
                .orElseThrow(DirectoryNotFoundException::new);
        directoryRepository.deleteById(id);
    }
}
