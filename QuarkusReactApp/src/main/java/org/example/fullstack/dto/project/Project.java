package org.example.fullstack.dto.project;


import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.example.fullstack.dto.user.User;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Entity
@Data
@Table(name = "projects",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name","user_id"})
})
public class Project  extends PanacheEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    public User user;

    @CreationTimestamp
    @Column (updatable = false, nullable = false)
    public ZonedDateTime created;

    @Version
    public int version;

}
