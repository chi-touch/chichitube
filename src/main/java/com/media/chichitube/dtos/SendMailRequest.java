package com.media.chichitube.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SendMailRequest {
    private String  RecipientEmail;
    private String  subject;
    private String  recipientName;
    private String htmlContent;
}
