package ru.coldwinternight.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.coldwinternight.todo.exception.InvalidDataAccessApiUsageDirectoriesFromUsersApiException;
import ru.coldwinternight.todo.exception.InvalidDataAccessApiUsageNotesFromUsersApiException;
import ru.coldwinternight.todo.exception.UserAlreadyExistException;
import ru.coldwinternight.todo.entity.UserEntity;
import ru.coldwinternight.todo.exception.UserNotFoundException;
import ru.coldwinternight.todo.model.User;
import ru.coldwinternight.todo.service.UserService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> index() {
        try {
            List<User> users = userService.readAll();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> showById(@PathVariable(name = "id") int id) {
        try {
            User user = userService.read(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserEntity user) {
        try {
            String successfullyCreatedMessage = "User created successfully";
            userService.create(user);
            return new ResponseEntity<>(successfullyCreatedMessage, HttpStatus.CREATED);
        } catch (UserAlreadyExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @Valid @RequestBody UserEntity user) {
        try {
            String updateMessage = "User successfully updated";
            userService.update(user, id);
            return new ResponseEntity<>(updateMessage, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InvalidDataAccessApiUsageDirectoriesFromUsersApiException |
                InvalidDataAccessApiUsageNotesFromUsersApiException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        try {
            String deleteMessage = "User successfully deleted";
            userService.delete(id);
            return new ResponseEntity<>(deleteMessage, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }
}