package com.media.chichitube.services;

import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import com.media.chichitube.dtos.requests.CreateUserRequest;
import com.media.chichitube.dtos.requests.UpdateMediaRequest;
import com.media.chichitube.dtos.requests.UploadMediaRequest;
import com.media.chichitube.dtos.responses.MediaResponse;
import com.media.chichitube.dtos.responses.UpdateMediaResponse;
import com.media.chichitube.dtos.responses.UploadMediaResponse;
import com.media.chichitube.exceptions.MediaHubBaseException;
import com.media.chichitube.exceptions.UserNotFoundException;
import com.media.chichitube.models.Category;
import com.media.chichitube.models.Media;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
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
import java.util.List;

import static com.media.chichitube.models.Category.*;
import static com.media.chichitube.utils.TestUtils.TEST_VIDEO_LOCATION;
import static com.media.chichitube.utils.TestUtils.buildUploadMediaRequest;
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
            request.setUserId(200L);
            UploadMediaResponse uploadResponse = mediaService.upload(request);

            assertThat(uploadResponse).isNotNull();
            assertThat(uploadResponse.getUrl()).isNotNull();
        }catch (IOException exception) {
            assertThat(exception).isNull();
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
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
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    @Test
    public void getMediaById(){
        Media media = mediaService.getMediaBy(1001L);
        log.info("found content ->{}",media);
        assertThat(media).isNotNull();
    }

    @Test
    @DisplayName("test update media files")
    public void testPartialUpdateMedia() throws JsonPointerException {

        Category category = mediaService.getMediaBy(1002L).getCategory();
        assertThat(category).isNotEqualTo(DRAMA);

//        UpdateMediaRequest updateMediaRequest = new UpdateMediaRequest();
//        updateMediaRequest.setUploader(STEP_MOM);


        List<JsonPatchOperation> operations = List.of(
                new ReplaceOperation(new JsonPointer("/category"), new TextNode(STEP_MOM.name()))
        );
        JsonPatch  updateMediaRequest= new JsonPatch(operations);
//        updateMediaRequest.setDescription("testing 123...");
       UpdateMediaResponse response = mediaService.updateMedia(1002L,
               updateMediaRequest);
       assertThat(response).isNotNull();

       category = mediaService.getMediaBy(1002L).getCategory();
       assertThat(category).isEqualTo(STEP_MOM);


    }

    @Test
    public void getMediaForUserTest() throws MediaHubBaseException {
        Long userId = 200L;
        List<MediaResponse>media = mediaService.getMediaFor(userId);
        log.info("media item -> {}",media);
        assertThat(media).hasSize(4);

    }








}
