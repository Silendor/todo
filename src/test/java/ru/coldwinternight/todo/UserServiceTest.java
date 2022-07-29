package ru.coldwinternight.todo;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.coldwinternight.todo.entity.UserEntity;
import ru.coldwinternight.todo.exception.UserNotFoundException;
import ru.coldwinternight.todo.model.User;
import ru.coldwinternight.todo.repository.UserRepository;
import ru.coldwinternight.todo.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @SneakyThrows
    @Test
    @DisplayName("Test read success")
    void testRead() {
        UserEntity userEntity = new UserEntity(1, "guideTheNPC", "guide@mail.com",
                "$2a$12$Gr7ODUcUq3.tJWCYdYmbbeme19KL9MMv6l4qtpAiMa5kbOi2TWziC",
                3, false, new ArrayList<>());
        doReturn(Optional.of(userEntity)).when(userRepository).findById(1);

        Optional<User> returnedUser = Optional.of(userService.read(1));

        Assertions.assertTrue(returnedUser.isPresent(), "User was not found");
        Assertions.assertEquals(returnedUser.get(), User.toModel(userEntity),"The user returned was not the same as the mock");
    }

    @Test
    @DisplayName("Test read not found")
    void testReadNotFound() {
        doReturn(Optional.empty()).when(userRepository).findById(1);

        UserNotFoundException thrown = Assertions.assertThrows(UserNotFoundException.class,
                () -> userService.read(1),
                "Expected read(1) to throw, but it didn't"
        );

        Assertions.assertTrue(thrown.getMessage().contains("User"));
    }

    @SneakyThrows
    @Test
    @DisplayName("Test readAll")
    void testReadAll() {
        UserEntity userEntity1 = new UserEntity(1, "guideTheNPC", "guide@mail.com",
                "$2a$12$Gr7ODUcUq3.tJWCYdYmbbeme19KL9MMv6l4qtpAiMa5kbOi2TWziC",
                3, false, new ArrayList<>());
        UserEntity userEntity2 = new UserEntity(2, "merchant", "trader@gmail.com",
                "some password",
                3, false, new ArrayList<>());
        doReturn(Arrays.asList(userEntity1, userEntity2)).when(userRepository).findAll();

        List<User> entities = userService.readAll();

        Assertions.assertEquals(2, entities.size(), "readAll should return 2 users");
    }
}
