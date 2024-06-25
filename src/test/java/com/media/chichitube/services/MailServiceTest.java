package com.media.chichitube.services;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;

//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class MailServiceTest {

    private MailService mailService;
    @Test
    public void testSendEmail(){
        String email = "";
       String  response = mailService.sendMail(email);
//
//       assertThat("response").isNotNull();
//       assertThat(response).containsIgnoreCase("Success");
    }
}
