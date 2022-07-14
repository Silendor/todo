package ru.coldwinternight.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.coldwinternight.todo.entity.TaskEntity;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {

    // All notes by user
    @Query("SELECT t FROM TaskEntity t WHERE t.user.id = :userId")
    List<TaskEntity> findAllByUserId(@Param("userId") int userId);

    @Query("SELECT t FROM TaskEntity t WHERE t.user.id = :userId AND t.today = true")
    List<TaskEntity> findAllByUserIdAndTodayIsTrue(int userId);

    // Completed
    List<TaskEntity> findAllByUserIdAndCompletedIsTrue(int userId);
    // Not completed
    List<TaskEntity> findAllByUserIdAndCompletedIsFalse(int userId);
}
