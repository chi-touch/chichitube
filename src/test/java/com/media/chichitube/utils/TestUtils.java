package com.media.chichitube.utils;

import com.media.chichitube.dtos.requests.UploadMediaRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static com.media.chichitube.models.Category.ACTION;

public class TestUtils {

    public static final String TEST_IMAGE_LOCATION ="C:\\Users\\User\\Documents\\chichitube\\src\\main\\resources\\static\\download.jpg";
    public static final String TEST_VIDEO_LOCATION ="C:\\Users\\User\\Documents\\chichitube\\src\\main\\resources\\static\\vid.mp4";

    public static UploadMediaRequest buildUploadMediaRequest(InputStream inputStream) throws IOException {
        UploadMediaRequest request = new UploadMediaRequest();
        MultipartFile file = new MockMultipartFile("media",inputStream);
        request.setMediaFile(file);
        request.setCategory(ACTION);
        request.setUserId(201L);

        return request;

    }
}
