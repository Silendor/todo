package ru.coldwinternight.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.coldwinternight.todo.entity.TaskEntity;
import ru.coldwinternight.todo.entity.UserEntity;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {

    // All notes by user
    @Query("SELECT t FROM TaskEntity t WHERE t.user.id = :userId")
    List<TaskEntity> findAllByUserId(@Param("userId") int userId);

    @Query("SELECT t FROM TaskEntity t WHERE t.user.id = :userId AND t.today = true")
    List<TaskEntity> findAllByUserIdAndTodayIsTrue(int userId);

    @Query("SELECT t FROM TaskEntity t WHERE t.user.id = :userId AND t.completed = true")
    List<TaskEntity> findAllByUserIdAndCompletedIsTrue(int userId);

    boolean existsByUserAndTodayIsTrue(UserEntity user);

    @Modifying
    @Query("DELETE FROM TaskEntity t WHERE t.id = :id")
    void deleteById(int id);

    @Modifying
    @Query("DELETE FROM TaskEntity t WHERE t.user.id = :id AND t.today = true")
    void deleteAllByTodayByUserId(int id);
}