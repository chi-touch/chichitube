package com.media.chichitube.exceptions;

public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException (String message) {
        super(message);
    }
}
