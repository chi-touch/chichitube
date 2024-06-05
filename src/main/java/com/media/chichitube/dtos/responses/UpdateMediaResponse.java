package com.media.chichitube.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.media.chichitube.models.Category;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
public class UpdateMediaResponse {

    private Long id;
    private String url;
    private String description;
    private Category category;
    @JsonProperty("created_at")
    private LocalDateTime timeCreated;
    @JsonProperty("updated_at")
    private LocalDateTime timeUpdated;
}
