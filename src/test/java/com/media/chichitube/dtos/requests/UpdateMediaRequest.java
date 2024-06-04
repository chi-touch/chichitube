package com.media.chichitube.dtos.requests;

import com.media.chichitube.models.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
public class UpdateMediaRequest {

    @Id
    private Long userid;
    private Category uploader;

}
