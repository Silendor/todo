package ru.coldwinternight.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.coldwinternight.todo.entity.NoteEntity;
import ru.coldwinternight.todo.entity.UserEntity;
import ru.coldwinternight.todo.exception.NoteNotFoundException;
import ru.coldwinternight.todo.exception.UserNotFoundException;
import ru.coldwinternight.todo.model.Note;
import ru.coldwinternight.todo.repository.NoteRepository;
import ru.coldwinternight.todo.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteService implements NoteServices{

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void create(NoteEntity note, int userId) throws UserNotFoundException {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        note.setUser(user);
        noteRepository.save(note);
    }

    @Override
    @Transactional
    public void reverseCompletedStatus(int id) throws NoteNotFoundException {
        NoteEntity note = noteRepository.findById(id)
                .orElseThrow(NoteNotFoundException::new);
        note.setCompleted(true);
        noteRepository.save(note);
    }

    @Override
    @Transactional
    public Note read(int id) throws NoteNotFoundException {
        NoteEntity noteEntity = noteRepository.findById(id)
                .orElseThrow(NoteNotFoundException::new);
        if (noteEntity.getDirectory() != null)
            return  Note.toModelWithDirectory(noteEntity);
        return Note.toModel(noteEntity);
    }

    @Override
    @Transactional
    public List<Note> readAllByUserId(int userId) throws UserNotFoundException, NoteNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return noteRepository.findAllByUser_Id(userId).stream().map(Note::toModel).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(NoteEntity note, int id) throws NoteNotFoundException {
        noteRepository.findById(id)
                .orElseThrow(NoteNotFoundException::new);
        note.setId(id);
        noteRepository.save(note);
    }

    @Override
    @Transactional
    public void delete(int id) throws NoteNotFoundException {
        noteRepository.findById(id)
                .orElseThrow(NoteNotFoundException::new);
        noteRepository.deleteById(id);
    }
}
