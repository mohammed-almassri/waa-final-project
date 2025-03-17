package miu.waa.group5.service;

import miu.waa.group5.entity.Media;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MediaService {

    /**
     * Uploads a media file, stores it, and returns its URL.
     *
     * @param file the MultipartFile to be uploaded
     * @return the URL where the uploaded media is accessible
     * @throws IOException if an error occurs during file upload
     */
    String uploadMedia(MultipartFile file) throws IOException;

    /**
     * Retrieves a media entity by its ID.
     *
     * @param id the ID of the media to retrieve
     * @return the media entity
     * @throws jakarta.persistence.EntityNotFoundException if no media is found with the given ID
     */
    Media getMedia(Long id);
}