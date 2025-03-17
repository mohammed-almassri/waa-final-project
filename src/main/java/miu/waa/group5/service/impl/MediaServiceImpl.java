package miu.waa.group5.service.impl;

import miu.waa.group5.entity.Media;
import miu.waa.group5.entity.MediaDetails;
import miu.waa.group5.repository.MediaDetailsRepository;
import miu.waa.group5.repository.MediaRepository;
import miu.waa.group5.service.MediaService;
import miu.waa.group5.service.StorageService;
import miu.waa.group5.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    private StorageService storageService;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private MediaDetailsRepository mediaDetailsRepository;

    @Override
    public String uploadMedia(MultipartFile file) throws IOException {
        String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String fileName = UUID.randomUUID().toString() + "." + fileExtension;

        String url = storageService.put(fileName, file);

        Media media = new Media();
        media.setFileName(file.getOriginalFilename());
        media.setFileType(file.getContentType());
        media.setSize(file.getSize());
        media.setUrl(url);

        mediaRepository.save(media);

        if (file.getContentType().startsWith("image")) {
            int[] size = ImageUtil.getImageSize(file.getInputStream());
            MediaDetails details = new MediaDetails();
            details.setWidth(size[0]);
            details.setHeight(size[1]);
            details.setMedia(media);
            mediaDetailsRepository.save(details);
        }

        return url;
    }

    @Override
    public Media getMedia(Long id) {
        return null;
    }
}
