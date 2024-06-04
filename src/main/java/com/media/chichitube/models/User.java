package com.media.chichitube.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.AUTO;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.LocalDateTime.now;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    @Setter(AccessLevel.NONE)
    private LocalDateTime timeCreated;

    @PrePersist
    private void setTimeCreated(){
        this.timeCreated =now();
    }

    @PreUpdate
    private void setTimeUpdated(){
        this.timeCreated =now();
    }

}
