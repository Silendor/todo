package ru.coldwinternight.todo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.coldwinternight.todo.configuration.UserInfo;
import ru.coldwinternight.todo.exception.IncorrectPasswordException;
import ru.coldwinternight.todo.exception.UserAlreadyExistException;
import ru.coldwinternight.todo.entity.UserEntity;
import ru.coldwinternight.todo.exception.UserNotFoundException;
import ru.coldwinternight.todo.model.User;
import ru.coldwinternight.todo.service.UserService;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController{
    private final UserService userService;
    private final UserInfo userInfo;

//    @GetMapping
//    public ResponseEntity<?> index() {
//        try {
//            log.info("Get all users");
//            List<User> users = userService.readAll();
//            return new ResponseEntity<>(users, HttpStatus.OK);
//        } catch (UserNotFoundException | UsernameNotFoundException e) {
//            log.error("Error, user not found: {}", e.getMessage());
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            log.error("Error while getting all users : {}", e.getMessage());
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }

    @GetMapping
    public ResponseEntity<?> showById() {
        try {
            Integer id = userInfo.getUserId();
            if (id == null)
                throw new UserNotFoundException();
            log.info("Get one user with id {}", id);
            User user = userService.read(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFoundException | UsernameNotFoundException e) {
            log.error("Error while get user: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error while get user: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserEntity user) {
        try {
            log.info("Create user {}", user);
            UserEntity userEntity = userService.create(user);
            String successfullyCreatedMessage = String.format("User with id %d created successfully", userEntity.getId());
            return new ResponseEntity<>(successfullyCreatedMessage, HttpStatus.CREATED);
        } catch (UserAlreadyExistException e) {
            log.error("Error creating user {}: {}", user, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error creating user {}: {}", user, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody UserEntity user) {
        try {
            Integer id = userInfo.getUserId();
            if (id == null)
                throw new UserNotFoundException();
            log.info("Updating user {} with id {}", user, id);
            String updateMessage = "User successfully updated";
            userService.update(user, id);
            return new ResponseEntity<>(updateMessage, HttpStatus.OK);
        } catch (UserNotFoundException | UsernameNotFoundException e) {
            log.error("Error while update user. User not found: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error while update user: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> passwords) {
        String oldPassword, newPassword;
        String updatePasswordMessage = "Password was successfully updated.";
        try {
            Integer id = userInfo.getUserId();
            log.info("Trying to change user's password. UserId: {}", id);
            oldPassword = passwords.get("oldpassword");
            newPassword = passwords.get("newpassword");

            if (id == null)
                throw new UserNotFoundException();

            if (oldPassword.equals(newPassword))
                return new ResponseEntity<>("These passwords are equal.", HttpStatus.NOT_MODIFIED);

            userService.updatePassword(oldPassword, newPassword, id);
            log.info("Password has changed for user {}", id);
            return new ResponseEntity<>(updatePasswordMessage, HttpStatus.OK);
        } catch (NullPointerException e) {
            log.error("Error while changing password, bad credentials: {}", e.getMessage());
            String mapErrorMessage = "You should to specify json with next keys: oldpassword, newpassword";
            return new ResponseEntity<>(mapErrorMessage, HttpStatus.BAD_REQUEST);
        } catch(IncorrectPasswordException e) {
            log.error("Error while changing password: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (UserNotFoundException e) {
            log.error("Error while changing password: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }  catch (Exception e) {
            log.error("Error while changing password: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> delete() {
        try {
            Integer id = userInfo.getUserId();
            if (id == null)
                throw new UserNotFoundException();
            log.info("Trying to delete user {}", id);
            String deleteMessage = "User successfully deleted";
            userService.delete(id);
            return new ResponseEntity<>(deleteMessage, HttpStatus.OK);
        } catch (UserNotFoundException | UsernameNotFoundException e) {
            log.error("Error while deleting user: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error while deleting user: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("/reverseRandomize")
    public ResponseEntity<?> reverseRandomizeTasks() {
        try {
            Integer id = userInfo.getUserId();
            if (id == null)
                throw new UserNotFoundException();
            log.info("Reverting randomize_today_tasks status");
            boolean status = userService.reverseRandomizeTasks(id);
            String statusReverseMessage = String.format("Randomize user tasks enabled: %b", status);
            return new ResponseEntity<>(statusReverseMessage, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            log.error("Error while reverting randomize_today_tasks: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error while reverting randomize_today_tasks: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }
}