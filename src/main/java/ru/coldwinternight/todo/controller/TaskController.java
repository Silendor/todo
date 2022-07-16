package ru.coldwinternight.todo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.coldwinternight.todo.configuration.UserInfo;
import ru.coldwinternight.todo.entity.TaskEntity;
import ru.coldwinternight.todo.exception.TaskNotFoundException;
import ru.coldwinternight.todo.exception.UserNotFoundException;
import ru.coldwinternight.todo.model.Task;
import ru.coldwinternight.todo.service.TaskService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserInfo userInfo;

    @GetMapping
    public ResponseEntity<?> index() {
        try {
            Integer userId = userInfo.getUserId();
            if (userId == null)
                throw new UserNotFoundException();
            log.info("Get all tasks for user {}", userId);
            List<Task> taskList = taskService.readAllByUserId(userId);
            return new ResponseEntity<>(taskList, HttpStatus.OK);
        } catch (UserNotFoundException | TaskNotFoundException e) {
            log.error("Error while getting all tasks for user: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error while getting all tasks for user: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> showById(@PathVariable(name = "id") int id) {
        try {
            log.info("Get one task {}", id);
            Integer userId = userInfo.getUserId();
            Task task = taskService.read(id);
            Integer taskUserId = task.getUserId();
            if (!taskUserId.equals(userId)) {
                log.warn("Attempting to read user's {} task {} by another user", taskUserId, id);
                return new ResponseEntity<>("You're not supposed to read this task.", HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            log.error("Error while getting task {}: {}", id, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error while getting task {}: {}", id, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/today")
    public ResponseEntity<?> today() {
        log.info("Get today tasks");
        try {
            Integer userId = userInfo.getUserId();
            if (userId == null)
                throw new UserNotFoundException();
            List<Task> todayTasks = taskService.readAllTodayTasksByUserId(userId);
            return new ResponseEntity<>(todayTasks, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            log.error("Error while getting today tasks: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            log.error("Error while getting today tasks: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TaskEntity task) {
        try {
            Integer userId = userInfo.getUserId();
            if (userId == null)
                throw new UserNotFoundException();
            log.info("Create new task {} for userId {}:", task, userId);
            TaskEntity taskEntity = taskService.create(task, userId);
            String successfullyCreatedMessage = String.format("Task with id %d created successfully",
                    taskEntity.getId());
            return new ResponseEntity<>(successfullyCreatedMessage, HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            log.error("Error while creating new task for user: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error while creating new task for user: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @Valid @RequestBody TaskEntity task) {
        try {
            log.info("Updating task {} with id: {}", task, id);
            Integer userId = userInfo.getUserId();
            Task taskDB = taskService.read(id);
            Integer taskUserId = taskDB.getUserId();
            if (taskUserId == null)
                throw new UserNotFoundException();
            if (!taskUserId.equals(userId)) {
                log.warn("Attempting to edit user's {} task {} by another user", taskUserId, id);
                return new ResponseEntity<>("You're not supposed to edit this task.", HttpStatus.FORBIDDEN);
            }
            taskService.update(task, id);
            String updateMessage = "Task successfully updated";
            return new ResponseEntity<>(updateMessage, HttpStatus.OK);
        } catch (TaskNotFoundException | UserNotFoundException e) {
            log.error("Error while updating task {}: {}", id, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error while updating task {}: {}", id, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        try {
            log.info("Deleting task {}", id);
            Integer userId = userInfo.getUserId();
            Task task = taskService.read(id);
            Integer taskUserId = task.getUserId();
            if (!taskUserId.equals(userId)) {
                log.warn("Attempting to delete user's {} task {} by another user", taskUserId, id);
                return new ResponseEntity<>("You're not supposed to delete this task.", HttpStatus.FORBIDDEN);
            }
            taskService.delete(id);
            String deleteMessage = "Task successfully deleted";
            return new ResponseEntity<>(deleteMessage, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            log.error("Error while deleting task {}: {}", id, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error while deleting task {}: {}", id, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("/{id}/reverseCompleted")
    public ResponseEntity<?> reverseCompleted(@PathVariable(name = "id") int id) {
        try {
            log.info("Reverting 'completed' field for task {}", id);
            Integer userId = userInfo.getUserId();
            Task task = taskService.read(id);
            Integer taskUserId = task.getUserId();
            if (!taskUserId.equals(userId)) {
                log.warn("Attempting to complete user's {} task {} by another user", taskUserId, id);
                return new ResponseEntity<>("You're not supposed to complete this task.", HttpStatus.FORBIDDEN);
            }
            boolean status = taskService.reverseCompleted(id);
            String statusReverseMessage = String.format("The task is completed: %b", status);
            return new ResponseEntity<>(statusReverseMessage, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            log.error("Error while reverting 'completed' field for task {}: {}", id, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error while reverting 'completed' field for task {}: {}", id, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("/{id}/reverseToday")
    public ResponseEntity<?> reverseToday(@PathVariable(name = "id") int id) {
        try {
            log.info("Reverting 'today' field for task {}", id);
            Integer userId = userInfo.getUserId();
            Task task = taskService.read(id);
            Integer taskUserId = task.getUserId();
            if (!taskUserId.equals(userId)) {
                log.warn("Attempting to set 'today' for user's {} task {} by another user", taskUserId, id);
                return new ResponseEntity<>("You're not supposed to set 'today' for this task.", HttpStatus.FORBIDDEN);
            }
            boolean status = taskService.reverseToday(id);
            String statusReverseMessage = String.format("New status of today: %b", status);
            return new ResponseEntity<>(statusReverseMessage, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            log.error("Error while reverting 'today' field for task {}: {}",id, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error while reverting 'today' field for task {}: {}",id, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("/{id}/reverseArchived")
    public ResponseEntity<?> reverseArchived(@PathVariable(name = "id") int id) {
        try {
            log.info("Reverting 'archived' field for task {}", id);
            Integer userId = userInfo.getUserId();
            Task task = taskService.read(id);
            Integer taskUserId = task.getUserId();
            if (!taskUserId.equals(userId)) {
                log.warn("Attempting to set 'archive' for user's {} task {} by another user", taskUserId, id);
                return new ResponseEntity<>("You're not supposed to set 'archive' for this task.", HttpStatus.FORBIDDEN);
            }
            boolean status = taskService.reverseArchived(id);
            String statusReverseMessage = String.format("New status of archived: %b", status);
            return new ResponseEntity<>(statusReverseMessage, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            log.error("Error while reverting 'archived' field for task {}: {}", id, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error while reverting 'archived' field for task {}: {}", id, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

}
