package org.example.fullstack.dto.task;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.example.fullstack.dto.project.Project;
import org.example.fullstack.dto.user.User;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Entity
@Data
@Table(name = "tasks")
public class Task extends PanacheEntity {

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(length = 1000)
    private Integer priority;


    @ManyToOne(optional = false)
    private User user;

    private ZonedDateTime complete;

    @ManyToOne
    private Project project;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private ZonedDateTime created;

    @Version
    private int version;
}
