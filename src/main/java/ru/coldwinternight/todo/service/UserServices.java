package ru.coldwinternight.todo.service;

import ru.coldwinternight.todo.model.User;

import java.util.List;

public interface UserServices {

    /**
    * Create a new user
    * @param user - user for creation
    */
    void create(User user);

    /**
     * Returns list of all users
     * @return list of users
     */
    List<User> readALl();

    /**
     * Returns user by id
     * @param id - users id
     * @return - users object with specified id
     */
    User read(int id);

    /**
     * Updates user by id
     * @param user - user with new data
     * @param id - id of the user to be updated
     * @return true if data was updated and false otherwise
     */
    boolean update(User user, int id);

    /**
     * Deletes user by id
     * @param id - id of user to be deleted
     * @return - true if user was deleted and false otherwise
     */
    boolean delete(int id);
}
