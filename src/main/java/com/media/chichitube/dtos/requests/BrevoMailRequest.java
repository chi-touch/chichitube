package com.media.chichitube.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class BrevoMailRequest {
    private Sender sender;

    @JsonProperty("to")
    private List<Recipient> recipient;
    private String  subject;
    @JsonProperty("htmlContent")
    private String content;
}
