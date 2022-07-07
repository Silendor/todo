package ru.coldwinternight.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.coldwinternight.todo.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT u from UserEntity u where u.email = LOWER(:email)")
    Optional<UserEntity> findByEmailIgnoreCase(@Param("email") String email);

    // By Username
    List<UserEntity> findByUsername(String username);

    @Modifying
    @Query("UPDATE UserEntity u SET u.password = :password WHERE u.id = :id")
    void updatePassword(@Param(value = "password") String password, @Param(value = "id") int id);

//    Optional<UserEntity> findFirstByUsername(String username);
    UserEntity findFirstByUsername(String username);
}
