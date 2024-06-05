package com.media.chichitube.dtos.responses;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class UserResponse {
    private Long id;

    private String email;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDateTime timeCreated;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDateTime timeUpdated;
}
