package miu.waa.group5.controller;

import miu.waa.group5.dto.UploadResponse;
import miu.waa.group5.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @PostMapping("/upload")
    public UploadResponse uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
            String url = mediaService.uploadMedia(file);
            return new UploadResponse(url);

    }

//    @GetMapping("/{id}")
//    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
//        byte[] data = mediaService.getMedia(id);
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(data);
//    }
}
