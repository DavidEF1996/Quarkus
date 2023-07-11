package org.example.fullstack.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Data
@Table(name="users")
public class User extends PanacheEntity {

    //Id aplicación
    @Column(unique = true, nullable = false)
    private String name;

    //Password para cifrar
    @Column(nullable = false)
    public String password;

    //Para auditoría, muestra la fecha de creación
    @CreationTimestamp
    @Column (updatable = false, nullable = false)
    private ZonedDateTime created;

    //Guarda el número de versión actual
    @Version
    private int version;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role")
    private List<String>roles;



    @JsonProperty("password")
    public void setPassword(String password){
        this.password = password;
    }
}
