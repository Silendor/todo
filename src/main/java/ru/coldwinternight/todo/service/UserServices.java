package ru.coldwinternight.todo.service;

import ru.coldwinternight.todo.exception.UserAlreadyExistException;
import ru.coldwinternight.todo.entity.UserEntity;
import ru.coldwinternight.todo.exception.UserNotFoundException;
import ru.coldwinternight.todo.model.User;

import java.util.List;

public interface UserServices {

    /**
    * Create a new user
    * @param user - user for creation
    */
    UserEntity create(UserEntity user) throws UserAlreadyExistException;

    /**
     * Returns list of all users
     * @return list of users
     */
    List<User> readAll() throws UserNotFoundException;

    /**
     * Returns user by id
     * @param id - users id
     * @return - users object with specified id
     */
    User read(int id) throws UserNotFoundException;

    /**
     * Updates user by id
     * @param user - user with new data
     * @param id - id of the user to be updated
     */
    void update(UserEntity user, int id) throws UserNotFoundException;

    /**
     * Deletes user by id
     * @param id - id of user to be deleted
     */
    void delete(int id) throws UserNotFoundException;
}
