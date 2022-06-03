package ru.coldwinternight.todo.controller;

import org.springframework.http.ResponseEntity;

public interface UniversalController {
//    ResponseEntity<?> index();
    ResponseEntity<?> showById(int id);
//    ResponseEntity<?> create(BaseEntity baseEntity);
//    ResponseEntity<?> update(int id, BaseEntity baseEntity);
    ResponseEntity<?> delete(int id);
}
