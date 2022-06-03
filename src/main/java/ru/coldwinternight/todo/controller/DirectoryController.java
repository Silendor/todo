package ru.coldwinternight.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.coldwinternight.todo.entity.DirectoryEntity;
import ru.coldwinternight.todo.exception.DirectoryNotFoundException;
import ru.coldwinternight.todo.exception.NoteNotFoundException;
import ru.coldwinternight.todo.exception.UserNotFoundException;
import ru.coldwinternight.todo.model.Directory;
import ru.coldwinternight.todo.model.Note;
import ru.coldwinternight.todo.service.DirectoryService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/directories")
public class DirectoryController implements UniversalController {
    private final DirectoryService directoryService;

    @Autowired
    public DirectoryController(DirectoryService directoryService) {
        this.directoryService = directoryService;
    }

    @GetMapping
    public ResponseEntity<?> index(@RequestParam(name = "userid", required = false) Integer userId) {
        try {
            if (userId == null)
                throw new IllegalStateException("userid parameter expected");

            List<Directory> directoryList = directoryService.readAllByUserId(userId);
            return new ResponseEntity<>(directoryList, HttpStatus.OK);
        } catch (UserNotFoundException | DirectoryNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showById(@PathVariable(name = "id") int id) {
        try {
            Directory directory = directoryService.read(id);
            return new ResponseEntity<>(directory, HttpStatus.OK);
        } catch (DirectoryNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam(name = "userid", required = false) Integer userId,
                                    @Valid @RequestBody DirectoryEntity directory) {
        try {
            if (userId == null)
                throw new IllegalStateException("userid parameter expected");

            String successfullyCreatedMessage = "Directory created successfully";
            directoryService.create(directory, userId);
            return new ResponseEntity<>(successfullyCreatedMessage, HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @Valid @RequestBody DirectoryEntity directory) {
        try {
            String updateMessage = "Directory successfully updated";
            directoryService.update(directory, id);
            return new ResponseEntity<>(updateMessage, HttpStatus.OK);
        } catch (DirectoryNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        try {
            String deleteMessage = "Directory successfully deleted";
            directoryService.delete(id);
            return new ResponseEntity<>(deleteMessage, HttpStatus.OK);
        } catch (DirectoryNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }
}
