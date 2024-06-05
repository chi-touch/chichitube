package com.media.chichitube.services;

import com.github.fge.jsonpatch.JsonPatch;
import com.media.chichitube.dtos.requests.UpdateMediaRequest;
import com.media.chichitube.dtos.requests.UploadMediaRequest;
import com.media.chichitube.dtos.responses.MediaResponse;
import com.media.chichitube.dtos.responses.UpdateMediaResponse;
import com.media.chichitube.dtos.responses.UploadMediaResponse;
import com.media.chichitube.models.Media;

import java.util.List;

public interface MediaService {
    UploadMediaResponse upload(UploadMediaRequest request);


    UploadMediaResponse uploadVideo(UploadMediaRequest request);

    Media getMediaBy(long id);

  UpdateMediaResponse update(Long mediaId,  JsonPatch updateMediaRequest);

    List<MediaResponse> getMediaFor(Long userId);
}
