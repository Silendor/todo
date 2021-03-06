package ru.coldwinternight.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.coldwinternight.todo.exception.*;
import ru.coldwinternight.todo.entity.UserEntity;
import ru.coldwinternight.todo.model.User;
import ru.coldwinternight.todo.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserServices, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserEntity create(UserEntity user) throws UserAlreadyExistException {
        if (userRepository.findByEmailIgnoreCase(user.getEmail()).isPresent()) {
            String userExistMessage = "User with this email already exists";
            throw new UserAlreadyExistException(userExistMessage);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // username that user login with = email
        UserEntity user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", email)));

        // don't have any authorities
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public UserEntity loadUserEntityByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional
    public void update(UserEntity user, int id) throws UserNotFoundException, EmailCannotBeNullException {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        if (user.getTasks() != null)
            throw new InvalidDataAccessApiUsageTasksFromUsersApiException();
        if (user.getEmail() == null)
            throw new EmailCannotBeNullException("Please, send Email field.");
        userEntity.setUsername(user.getUsername());
        userEntity.setEmail(user.getEmail());
        userEntity.setRandomizeTodayTasks(user.isRandomizeTodayTasks());
        userEntity.setTodayAmount(user.getTodayAmount());
        userRepository.save(userEntity);
    }

    @Transactional
    public void updatePassword(String oldPassword, String newPassword, int id)
            throws UserNotFoundException, IncorrectPasswordException {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(oldPassword, user.getPassword()))
            throw new IncorrectPasswordException("Incorrect old password.");

        userRepository.updatePassword(passwordEncoder.encode(newPassword), id);
    }

    @Transactional
    public void updateUsername(int id, String newUsername) throws UserNotFoundException {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        userEntity.setUsername(newUsername);
        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void delete(int id) throws UserNotFoundException {
        userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(id);
    }

    @Transactional
    public boolean reverseRandomizeTasks(int id) throws UserNotFoundException {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        user.setRandomizeTodayTasks(!user.isRandomizeTodayTasks());
        userRepository.save(user);
        return user.isRandomizeTodayTasks();
    }
}
