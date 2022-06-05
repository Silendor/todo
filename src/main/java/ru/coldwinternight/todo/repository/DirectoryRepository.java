package ru.coldwinternight.todo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.coldwinternight.todo.entity.DirectoryEntity;

import java.util.Optional;

public interface DirectoryRepository extends JpaRepository<DirectoryEntity, Integer> {

    // All directories by user
    @Query("SELECT n FROM DirectoryEntity n WHERE n.user.id = :userId")
    Optional<DirectoryEntity> findAllByUser_Id(@Param("userId") int userId);
//    List<DirectoryEntity> findAllByUser_Id(@Param("userId") int userId);

}
