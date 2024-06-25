package com.media.chichitube.respositories;

import com.media.chichitube.exceptions.MediaHubBaseException;
import com.media.chichitube.exceptions.UserNotFoundException;
import com.media.chichitube.models.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {
    @Query("SELECT m FROM Media m where m.uploader.id =:userId")
    List<Media> findAllMediaFor(Long userId) throws MediaHubBaseException;
}
