package com.media.chichitube.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import com.media.chichitube.dtos.requests.UploadMediaRequest;
import com.media.chichitube.dtos.responses.UploadMediaResponse;
import com.media.chichitube.exceptions.MediaNotFoundException;
import com.media.chichitube.exceptions.MediaUploadFailedException;
import com.media.chichitube.models.Media;
import com.media.chichitube.models.User;
import com.media.chichitube.respositories.MediaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j

public class MavericksHubMediaServices  implements MediaService{

    @Autowired
    private final MediaRepository mediaRepository;

//    public MavericksHubMediaServices(MediaRepository mediaRepository) {
//        this.mediaRepository = mediaRepository;
//    }

    private final Cloudinary cloudinary;
    private final ModelMapper modelMapper;
    private final UserService userService;
    @Override
    public UploadMediaResponse upload(UploadMediaRequest request){
        User user = userService.getById(request.getUserId());
        try {

            Uploader uploader =cloudinary.uploader();
            Map<?,?> response = uploader.upload(request.getMediaFile().getBytes(),ObjectUtils.asMap("resource_type","auto"));
//            log.info("clodinary upload response::{}, response");
            UploadMediaResponse mediaResponse = new UploadMediaResponse();
            String url = response.get("url").toString();
           Media media = modelMapper.map(request,Media.class);
           media.setUrl(url);
           media.setUploader(user);
            media = mediaRepository.save(media);
            return modelMapper.map(media,UploadMediaResponse.class);
        }catch (IOException exception){
            throw new MediaUploadFailedException("media upload failed");



        }

    }

    @Override
    public UploadMediaResponse uploadVideo(UploadMediaRequest request) {
        try{
            Uploader uploader = cloudinary.uploader();
            Map<?,?> response = uploader.uploadLarge(request.getMediaFile().getBytes(), ObjectUtils.asMap("resource_type", "video"));
            UploadMediaResponse mediaResponse = new UploadMediaResponse();
            String url = response.get("url").toString();
            Media media = modelMapper.map(request,Media.class);
            media.setUrl(url);
            media = mediaRepository.save(media);
            return modelMapper.map(media,UploadMediaResponse.class);
        }catch (IOException e){
            throw new MediaUploadFailedException("media upload failed");

        }

    }

    @Override
    public Media getMediaBy(long id) {
        return mediaRepository.findById(id).orElseThrow(()-> new MediaNotFoundException("media not found"));

    }




}
