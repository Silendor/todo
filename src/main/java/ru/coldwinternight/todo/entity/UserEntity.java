package ru.coldwinternight.todo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"new"})
@ToString(callSuper = true, exclude = {"password"})
public class UserEntity extends BaseEntity {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user-seq")
    @SequenceGenerator(name = "user-seq", sequenceName = "user_id_seq", allocationSize = 1)
    private Integer id;

//    @Size(max = 200)
//    @Column(nullable = false)
    @Column
    private String username;

//    @Size(max = 254)
//    @NotEmpty
    @Email
    @Column(nullable = false, unique = true)
    private String email;

//    @Size(max = 200)
//    @NotEmpty
    @Column(nullable = false)
    private String password;

//    private Date registerDate;
//    private Date lastLogin;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade=CascadeType.REMOVE)
    private List<NoteEntity> notes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade=CascadeType.REMOVE)
    private List<DirectoryEntity> directories;
}
