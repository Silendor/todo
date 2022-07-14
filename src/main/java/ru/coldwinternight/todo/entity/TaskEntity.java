package ru.coldwinternight.todo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "tasks")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"new"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class TaskEntity extends BaseEntity{
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task-seq")
    @SequenceGenerator(name = "task-seq", sequenceName = "task_id_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "varchar(254) default ''")
    private String taskBody = "";

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean completed;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean today;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean archived;
}
