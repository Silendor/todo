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
    @Transactional
    public void reverseCompletedStatus(int id) throws TaskNotFoundException {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);
        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);
    }

    @Override
    public Task read(int id) throws TaskNotFoundException {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);
        return Task.toModel(taskEntity);
    }

    @Override
    public List<Task> readAllByUserId(int userId) throws UserNotFoundException, TaskNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return taskRepository.findAllByUser_Id(userId).stream().map(Task::toModel).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(TaskEntity task, int id) throws TaskNotFoundException {
        taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);
        task.setId(id);
        taskRepository.save(task);
    }

    @Override
    @Transactional
    public void delete(int id) throws TaskNotFoundException {
        taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);
        taskRepository.deleteById(id);
    }
}
