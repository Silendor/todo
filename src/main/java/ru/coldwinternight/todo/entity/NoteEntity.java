package ru.coldwinternight.todo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "notes")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"new"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class NoteEntity extends BaseEntity{
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "note-seq")
    @SequenceGenerator(name = "note-seq", sequenceName = "note_id_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "directory_id")
    private DirectoryEntity directory;

    @Column(nullable = false)
    private String note;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private boolean completed;
}
