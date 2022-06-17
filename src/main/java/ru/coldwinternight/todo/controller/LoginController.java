package ru.coldwinternight.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.coldwinternight.todo.service.UserService;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) { this.userService = userService; }

    @GetMapping
    public ResponseEntity<?> login() {
//        not implemented
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register() {
//        not implemented
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
//        not implemented
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
