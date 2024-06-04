package com.media.chichitube.services;

import com.media.chichitube.dtos.requests.UploadMediaRequest;
import com.media.chichitube.dtos.responses.UploadMediaResponse;
import com.media.chichitube.models.Category;
import com.media.chichitube.models.Media;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.media.chichitube.models.Category.ACTION;
import static com.media.chichitube.utils.TestUtils.TEST_VIDEO_LOCATION;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Slf4j
@Sql(scripts = {"/db/data.sql"})
public class MediaServiceTest {

    @Autowired
    private MediaService mediaService;

    @Test
    public void uploadMediaTest() {
        String fileLocation = "C:\\Users\\User\\Documents\\chichitube\\src\\main\\resources\\static\\download.jpg";
        Path path = Paths.get(fileLocation);

        try(var inputStream = Files.newInputStream(path)) {
            Files.newInputStream(path);
            UploadMediaRequest request = new UploadMediaRequest();
            MultipartFile file = new MockMultipartFile("photographer", inputStream);
            request.setMediaFile(file);
            UploadMediaResponse uploadResponse = mediaService.upload(request);

            assertThat(uploadResponse).isNotNull();
            assertThat(uploadResponse.getUrl()).isNotNull();
        }catch (IOException exception) {
            assertThat(exception).isNull();
        }
    }

    @Test
    @Sql(scripts = {"/db/data.sql"})
    public void uploadMediaVideoTest() {
        Path path = Paths.get(TEST_VIDEO_LOCATION);

        try (var inputStream = Files.newInputStream(path)) {
            UploadMediaRequest request = buildUploadMediaRequest(inputStream);
            UploadMediaResponse response = mediaService.upload(request);
            log.info("response--> {}",response);
            assertThat(response).isNotNull();
            assertThat(response.getUrl()).isNotNull();

        } catch (IOException e) {
            assertThat(e).isNotNull();
        }
    }

    private static UploadMediaRequest buildUploadMediaRequest(InputStream inputStream) throws IOException {
        UploadMediaRequest request = new UploadMediaRequest();
        MultipartFile file = new MockMultipartFile("media",inputStream);
        request.setMediaFile(file);
        request.setCategory(ACTION);
        request.setUserId(201L);

        return request;

    }

    @Test

    public void getMediaById(){
        Media media = mediaService.getMediaBy(1001L);
        log.info("found content ->{}",media);
        assertThat(media).isNotNull();
    }








}
