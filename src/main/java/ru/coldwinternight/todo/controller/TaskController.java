package ru.coldwinternight.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.coldwinternight.todo.entity.TaskEntity;
import ru.coldwinternight.todo.exception.TaskNotFoundException;
import ru.coldwinternight.todo.exception.UserNotFoundException;
import ru.coldwinternight.todo.model.Task;
import ru.coldwinternight.todo.service.TaskService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController implements UniversalController {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<?> index(@RequestParam(name = "userid", required = false) Integer userId) {
        try {
            if (userId == null)
                throw new IllegalStateException("userid parameter expected");

            List<Task> taskList = taskService.readAllByUserId(userId);
            return new ResponseEntity<>(taskList, HttpStatus.OK);
        } catch (UserNotFoundException | TaskNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> showById(@PathVariable(name = "id") int id) {
        try {
            Task task = taskService.read(id);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam(name = "userid", required = false) Integer userId,
                                    @Valid @RequestBody TaskEntity task) {
        try {
            if (userId == null)
                throw new IllegalStateException("userid parameter expected");

            String successfullyCreatedMessage = "Task created successfully";
            taskService.create(task, userId);
            return new ResponseEntity<>(successfullyCreatedMessage, HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> reverseCompletedStatus(@PathVariable(name = "id") int id) {
        try {
            String statusReverseMessage = "The task status has reversed";
            taskService.reverseCompletedStatus(id);
            return new ResponseEntity<>(statusReverseMessage, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @Valid @RequestBody TaskEntity task) {
        try {
            String updateMessage = "Task successfully updated";
            taskService.update(task, id);
            return new ResponseEntity<>(updateMessage, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        try {
            String deleteMessage = "Task successfully deleted";
            taskService.delete(id);
            return new ResponseEntity<>(deleteMessage, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }
}
