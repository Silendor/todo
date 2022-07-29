package ru.coldwinternight.todo;

import ru.coldwinternight.todo.controller.TaskController;
import ru.coldwinternight.todo.controller.UserController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private UserController userController;

    @Autowired
    private TaskController taskController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(userController).isNotNull();
        assertThat(taskController).isNotNull();
    }
}
