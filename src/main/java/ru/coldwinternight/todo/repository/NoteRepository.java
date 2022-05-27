package ru.coldwinternight.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.coldwinternight.todo.entity.NoteEntity;

import java.util.List;

@Transactional(readOnly = true)
public interface NoteRepository extends JpaRepository<NoteEntity, Integer> {

    // All notes by user
    @Query("SELECT n FROM NoteEntity n WHERE n.user.id = :userId")
    List<NoteEntity> findAllByUser_Id(@Param("userId") int userId);

    // Notes in directory
    List<NoteEntity> findAllByUser_IdAndDirectory_Id(int userId, int directoryId);
    List<NoteEntity> findAllByUser_IdAndDirectoryNameIgnoreCase(int userId, String directoryName);

    // Completed
    List<NoteEntity> findAllByUser_IdAndCompletedIsTrue(int userId);

    // Not completed
    List<NoteEntity> findAllByUser_IdAndCompletedIsFalse(int userId);
}
