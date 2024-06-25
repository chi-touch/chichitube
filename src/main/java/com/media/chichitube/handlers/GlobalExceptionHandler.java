package com.media.chichitube.handlers;

import com.media.chichitube.exceptions.MediaUploadFailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

//@ControllerAdvice //is used to send custom error message when dealing with mbc achtecture\
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MediaUploadFailedException.class)
    @ResponseBody
    public ResponseEntity <?> handleMediaUploadFailed(MediaUploadFailedException exception){
        return ResponseEntity.status(BAD_REQUEST).body(Map.of("error",exception.getMessage(),"success",false));

    }
}
