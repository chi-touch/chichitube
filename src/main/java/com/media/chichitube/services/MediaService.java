package com.media.chichitube.services;

import com.media.chichitube.dtos.requests.UploadMediaRequest;
import com.media.chichitube.dtos.responses.UploadMediaResponse;
import com.media.chichitube.models.Media;

public interface MediaService {
    UploadMediaResponse upload(UploadMediaRequest request);


    UploadMediaResponse uploadVideo(UploadMediaRequest request);

    Media getMediaBy(long id);
}
