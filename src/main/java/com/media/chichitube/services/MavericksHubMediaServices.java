package com.media.chichitube.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.media.chichitube.dtos.requests.UploadMediaRequest;
import com.media.chichitube.dtos.responses.MediaResponse;
import com.media.chichitube.dtos.responses.UpdateMediaResponse;
import com.media.chichitube.dtos.responses.UploadMediaResponse;
import com.media.chichitube.exceptions.*;
import com.media.chichitube.models.Media;
import com.media.chichitube.models.User;
import com.media.chichitube.respositories.MediaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
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
    public UploadMediaResponse upload(UploadMediaRequest request) throws UserNotFoundException {
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

    @Override
    public UpdateMediaResponse updateMedia(Long mediaId,
                                      JsonPatch updateMediaRequest) {
       try {
           // 1. get target objecta
           Media media = getMediaBy(mediaId);
          // 2. convert object from above to JsonNode (use ObjectMapper)
           ObjectMapper objectMapper = new ObjectMapper();
           //ObjectMapper convert the media into a JsonNode
           JsonNode mediaNode = objectMapper.convertValue(media, JsonNode.class);
          // 3. apply jsonPatch to mediaNode
           mediaNode = updateMediaRequest.apply(mediaNode);

          // 4. convert mediaNode to Media object;
           media = objectMapper.convertValue(media, Media.class);
           media = mediaRepository.save(media);

           return modelMapper.map(media,UpdateMediaResponse.class);
       }catch (JsonPatchException exception){
           throw new MediaUpdateException(exception.getMessage());

       }

    }

    @Override
    public List<MediaResponse> getMediaFor(Long userId) throws MediaHubBaseException {
        userService.getById(userId);
        List<Media> media = mediaRepository.findAllMediaFor(userId);
        return media.stream()
                .map(mediaItem ->modelMapper.map(mediaItem, MediaResponse.class))
                .toList();
    }




}
