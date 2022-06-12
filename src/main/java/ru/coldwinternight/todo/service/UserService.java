package ru.coldwinternight.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.coldwinternight.todo.exception.InvalidDataAccessApiUsageTasksFromUsersApiException;
import ru.coldwinternight.todo.exception.UserAlreadyExistException;
import ru.coldwinternight.todo.entity.UserEntity;
import ru.coldwinternight.todo.exception.UserNotFoundException;
import ru.coldwinternight.todo.model.User;
import ru.coldwinternight.todo.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService implements UserServices {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void create(UserEntity user) throws UserAlreadyExistException {
//        if (userRepository.findByUsername(user.getUsername()) != null) {
        if (userRepository.findByEmailIgnoreCase(user.getEmail()).isPresent()) {
            String userExistMessage = "User with this email already exists";
            throw new UserAlreadyExistException(userExistMessage);
        }
        userRepository.save(user);
    }

    @Override
    public List<User> readAll() throws UserNotFoundException {
        if (userRepository.findAll().isEmpty()) {
            throw new UserNotFoundException();
        }
        return userRepository.findAll().stream().map(User::toModel).collect(Collectors.toList());
    }

    @Override
    public User read(int id) throws UserNotFoundException {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return User.toModel(user);
    }

    @Override
    @Transactional
    public void update(UserEntity user, int id) throws UserNotFoundException {
        userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        if (user.getTasks() != null)
            throw new InvalidDataAccessApiUsageTasksFromUsersApiException();
        user.setId(id);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(int id) throws UserNotFoundException {
        userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(id);
    }
}
