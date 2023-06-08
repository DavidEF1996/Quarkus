package org.example.fullstack.task;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.*;
import org.example.fullstack.project.Project;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "tasks")
public class Task extends PanacheEntity {

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    private Integer priority;

    @ManyToOne
    private Project project;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private ZonedDateTime created;

    @Version
    private int version;
}
