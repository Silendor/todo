package ru.coldwinternight.todo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "users")
@Proxy(lazy = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"password"})
public class User {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user-seq")
    @SequenceGenerator(name = "user-seq", sequenceName = "user_id_sequence", allocationSize = 1)
    private Integer id;

    @Size(max = 200)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Email
    @Size(max = 254)
    @NotEmpty
    @Column(name = "email", nullable = false)
    private String email;

    @Size(max = 50)
    @NotEmpty
    @Column(name = "password", nullable = false)
    private String password;
//    private String registerDate;

}
