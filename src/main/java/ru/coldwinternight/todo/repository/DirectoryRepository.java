package ru.coldwinternight.todo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.coldwinternight.todo.entity.DirectoryEntity;

@Transactional(readOnly = true)
public interface DirectoryRepository extends JpaRepository<DirectoryEntity, Integer> {
}
