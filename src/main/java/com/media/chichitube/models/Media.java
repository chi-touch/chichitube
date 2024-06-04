package com.media.chichitube.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static java.time.LocalDateTime.now;

@Setter
@Getter
@Entity
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String description;
    @Enumerated(value = STRING)
    private Category category;
    @Setter(AccessLevel.NONE)
    private LocalDateTime timeCreated;
    @ManyToOne
    private User uploader;

    @PrePersist
    private void setTimeCreated(){
        this.timeCreated = now();
    }

    private void setTimeUpdate(){
        this.timeCreated = now();
    }
}
