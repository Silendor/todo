package ru.coldwinternight.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.coldwinternight.todo.entity.TaskEntity;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {

    // All notes by user
    @Query("SELECT t FROM TaskEntity t WHERE t.user.id = :userId")
//    Optional<TaskEntity> findAllByUser_Id(@Param("userId") int userId);
    List<TaskEntity> findAllByUser_Id(@Param("userId") int userId);

    // Completed
    List<TaskEntity> findAllByUser_IdAndCompletedIsTrue(int userId);

    // Not completed
    List<TaskEntity> findAllByUser_IdAndCompletedIsFalse(int userId);
}
