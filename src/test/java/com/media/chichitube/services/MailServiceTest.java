package com.media.chichitube.services;

import com.media.chichitube.dtos.SendMailRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;
    @Test
    public void testSendEmail(){
        String email = "gloebudav@gmail.com";
        SendMailRequest mailRequest = new SendMailRequest();
        mailRequest.setRecipientEmail(email);
        mailRequest.setRecipientName("ada");
        mailRequest.setHtmlContent("<p>Hello from the other side</p>");
        mailRequest.setSubject("Hello");
       String  response = mailService.sendMail(mailRequest);

       assertThat(response).isNotNull();
       assertThat(response).containsIgnoringCase("Success");
    }
}
