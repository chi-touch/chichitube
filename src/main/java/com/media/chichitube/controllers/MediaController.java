package com.media.chichitube.controllers;


import com.media.chichitube.dtos.requests.UploadMediaRequest;
import com.media.chichitube.exceptions.MediaHubBaseException;
import com.media.chichitube.exceptions.UserNotFoundException;
import com.media.chichitube.models.Media;
import com.media.chichitube.services.MediaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/media")
@AllArgsConstructor
public class MediaController {


    private final MediaService mediaService;


    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadMedia(@ModelAttribute UploadMediaRequest uploadMediaRequest) throws UserNotFoundException {
        return ResponseEntity.status(CREATED)
                .body(mediaService.upload(uploadMediaRequest));
    }

    @GetMapping
    public ResponseEntity<?> getMediaForUser(@RequestParam Long userId) throws UserNotFoundException {
       return ResponseEntity.ok(mediaService.getMediaFor(userId));
    }

}
