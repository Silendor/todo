package ru.coldwinternight.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.coldwinternight.todo.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
