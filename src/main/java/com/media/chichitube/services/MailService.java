package com.media.chichitube.services;

import com.media.chichitube.dtos.SendMailRequest;

public interface MailService {

    String sendMail(SendMailRequest request);
}
