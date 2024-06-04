package com.media.chichitube.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.media.chichitube.models.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UploadMediaResponse {

    private Long id;
    @JsonProperty("media_description")
    private String description;
    @JsonProperty("media_url")
    private String url;
    private Category category;
}
