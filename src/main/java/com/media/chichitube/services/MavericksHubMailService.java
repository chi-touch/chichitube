package com.media.chichitube.services;

import com.media.chichitube.config.MailConfig;
import com.media.chichitube.dtos.SendMailRequest;
import com.media.chichitube.dtos.requests.BrevoMailRequest;
import com.media.chichitube.dtos.requests.Recipient;
import com.media.chichitube.dtos.requests.Sender;
import com.media.chichitube.dtos.responses.BrevoMailResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@AllArgsConstructor
public class MavericksHubMailService implements MailService{

    private final MailConfig mailConfig;
    @Override
    public String sendMail(SendMailRequest mailRequest) {
        RestTemplate restTemplate = new RestTemplate();
        String url = mailConfig.getMailApiUrl();
        BrevoMailRequest request = new BrevoMailRequest();
        request.setSubject(mailRequest.getSubject());
        request.setSender(new Sender());
        request.setRecipient(
                List.of(new Recipient(mailRequest.getRecipientName(),
                mailRequest.getRecipientEmail())
                )
        );
        request.setContent(mailRequest.getHtmlContent());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.set("api-key", mailConfig.getMailApiKey());
        headers.set("accept",APPLICATION_JSON.toString());
        RequestEntity<?> httpRequest = new RequestEntity<>(request,headers,
                HttpMethod.POST, URI.create(url));
        ResponseEntity<BrevoMailResponse> response = restTemplate.postForEntity(url,httpRequest,BrevoMailResponse.class);

//        restTemplate.postForEntity(url,request, BrevoMailResponse.class);
        if(response.getBody() != null &&
                response.getStatusCode() == HttpStatusCode.valueOf(201))
            return "mail sent successfully";
        else throw new RuntimeException("email sending failed");
    }
}
