package ru.coldwinternight.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.coldwinternight.todo.entity.TaskEntity;
import ru.coldwinternight.todo.entity.UserEntity;
import ru.coldwinternight.todo.exception.TaskNotFoundException;
import ru.coldwinternight.todo.exception.UserNotFoundException;
import ru.coldwinternight.todo.model.Task;
import ru.coldwinternight.todo.repository.TaskRepository;
import ru.coldwinternight.todo.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService implements TaskServices {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TaskEntity create(TaskEntity task, int userId) throws UserNotFoundException {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        task.setUser(user);
        taskRepository.save(task);
        return task;
    }

    @Override
    public Task read(int id) throws TaskNotFoundException {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);
        return Task.toModel(taskEntity);
    }

    public List<Task> readAllTodayTasksByUserId(int userId) throws UserNotFoundException, TaskNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return taskRepository.findAllByUserIdAndTodayIsTrue(userId)
                .stream().map(Task::toModel).collect(Collectors.toList());
    }

    public List<Task> readAllCompletedTasksByUserId(int userId) throws UserNotFoundException, TaskNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return taskRepository.findAllByUserIdAndCompletedIsTrue(userId)
                .stream().map(Task::toModel).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(TaskEntity task, int id) throws TaskNotFoundException {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);
        taskEntity.setTitle(task.getTitle());
        taskEntity.setTaskBody(task.getTaskBody());
        taskEntity.setCompleted(task.isCompleted());
        taskEntity.setToday(task.isToday());
        taskEntity.setArchived(task.isArchived());
        taskRepository.save(taskEntity);
    }

    @Override
    @Transactional
    public void delete(int id) throws TaskNotFoundException {
        taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);
        taskRepository.deleteById(id);
    }

    @Transactional
    public void deleteToday(int userId) throws UserNotFoundException, TaskNotFoundException {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        if (!taskRepository.existsByUserAndTodayIsTrue(userEntity))
            throw new TaskNotFoundException("User must have at least one 'today' task.");
        taskRepository.deleteAllByTodayByUserId(userId);
    }

    @Override
    public List<Task> readAllByUserId(int userId) throws UserNotFoundException, TaskNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return taskRepository.findAllByUserId(userId).stream().map(Task::toModel).collect(Collectors.toList());
    }

    @Transactional
    public boolean reverseCompleted(int id) throws TaskNotFoundException {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);
        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);
        return task.isCompleted();
    }

    @Transactional
    public boolean reverseToday(int id) throws TaskNotFoundException {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);
        task.setToday(!task.isToday());
        taskRepository.save(task);
        return task.isToday();
    }

    @Transactional
    public boolean reverseArchived(int id) throws TaskNotFoundException {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);
        task.setArchived(!task.isArchived());
        taskRepository.save(task);
        return task.isArchived();
    }

}
