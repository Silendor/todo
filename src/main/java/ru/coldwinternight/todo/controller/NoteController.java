package ru.coldwinternight.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.coldwinternight.todo.entity.NoteEntity;
import ru.coldwinternight.todo.exception.NoteNotFoundException;
import ru.coldwinternight.todo.exception.UserNotFoundException;
import ru.coldwinternight.todo.model.Note;
import ru.coldwinternight.todo.service.NoteService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController implements UniversalController {
    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<?> index(@RequestParam(name = "userid", required = false) Integer userId) {
        try {
            if (userId == null)
                throw new IllegalStateException("userid parameter expected");

            List<Note> noteList = noteService.readAllByUserId(userId);
            return new ResponseEntity<>(noteList, HttpStatus.OK);
        } catch (UserNotFoundException | NoteNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> showById(@PathVariable(name = "id") int id) {
        try {
            Note note = noteService.read(id);
            return new ResponseEntity<>(note, HttpStatus.OK);
        } catch (NoteNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam(name = "userid", required = false) Integer userId,
                                    @Valid @RequestBody NoteEntity note) {
        try {
            if (userId == null)
                throw new IllegalStateException("userid parameter expected");

            String successfullyCreatedMessage = "Note created successfully";
            noteService.create(note, userId);
            return new ResponseEntity<>(successfullyCreatedMessage, HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> reverseCompletedStatus(@PathVariable(name = "id") int id) {
        try {
            String statusReverseMessage = "The note status has reversed";
            noteService.reverseCompletedStatus(id);
            return new ResponseEntity<>(statusReverseMessage, HttpStatus.OK);
        } catch (NoteNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @Valid @RequestBody NoteEntity note) {
        try {
            String updateMessage = "Note successfully updated";
            noteService.update(note, id);
            return new ResponseEntity<>(updateMessage, HttpStatus.OK);
        } catch (NoteNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        try {
            String deleteMessage = "Note successfully deleted";
            noteService.delete(id);
            return new ResponseEntity<>(deleteMessage, HttpStatus.OK);
        } catch (NoteNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }
}
