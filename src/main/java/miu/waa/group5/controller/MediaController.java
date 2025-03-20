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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @PostMapping("/upload")
    public UploadResponse uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        List<CompletableFuture<String>> uploadFutures = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                    try {
                        return mediaService.uploadMedia(file);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to upload file: " + file.getOriginalFilename(), e);
                    }
                });
                uploadFutures.add(future);
            }
        }

        // Wait for all uploads to complete
        CompletableFuture<Void> allDone = CompletableFuture.allOf(
                uploadFutures.toArray(new CompletableFuture[0])
        );

        try {
            // Block until all uploads are done
            allDone.get();

            // Collect results
            List<String> urls = uploadFutures.stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException("Error retrieving upload result", e);
                        }
                    })
                    .toList();

            return new UploadResponse(urls);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("Error processing uploads", e);
        }
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
//        byte[] data = mediaService.getMedia(id);
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(data);
//    }
}
