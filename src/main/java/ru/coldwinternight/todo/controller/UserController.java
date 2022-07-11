package ru.coldwinternight.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.coldwinternight.todo.exception.IncorrectPasswordException;
import ru.coldwinternight.todo.exception.UserAlreadyExistException;
import ru.coldwinternight.todo.entity.UserEntity;
import ru.coldwinternight.todo.exception.UserNotFoundException;
import ru.coldwinternight.todo.model.User;
import ru.coldwinternight.todo.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UniversalController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> index() {
        try {
            List<User> users = userService.readAll();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (UserNotFoundException | UsernameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> showById(@PathVariable(name = "id") int id) {
        try {
            User user = userService.read(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFoundException | UsernameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @Valid @RequestBody UserEntity user) {
        try {
            String updateMessage = "User successfully updated";
            userService.update(user, id);
            return new ResponseEntity<>(updateMessage, HttpStatus.OK);
        } catch (UserNotFoundException | UsernameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping(value = "/{id}/password")
    public ResponseEntity<?> updatePassword(@PathVariable(name = "id") int id,
                                            @RequestBody Map<String, String> passwords) {
        String oldPassword, newPassword;
        try {
            oldPassword = passwords.get("oldpassword");
            newPassword = passwords.get("newpassword");

            if (oldPassword.equals(newPassword))
                return new ResponseEntity<>("These passwords are equal.", HttpStatus.NOT_MODIFIED);

        } catch (NullPointerException e) {
            String mapErrorMessage = "You should to specify json with next keys: oldpassword, newpassword";
            return new ResponseEntity<>(mapErrorMessage, HttpStatus.BAD_REQUEST);
        }  catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


        try {
            String updatePasswordMessage = "Password was successfully updated.";
            userService.updatePassword(oldPassword, newPassword, id);
            return new ResponseEntity<>(updatePasswordMessage, HttpStatus.OK);
        } catch(IncorrectPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
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
        } catch (UserNotFoundException | UsernameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }
}