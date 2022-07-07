package ru.coldwinternight.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.coldwinternight.todo.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register() {
//        not implemented
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
//        not implemented
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
