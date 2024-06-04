package com.media.chichitube.exceptions;

public class MediaUploadFailedException extends RuntimeException{
    public MediaUploadFailedException(String message){
        super(message);
    }
}
