package miu.waa.group5.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    String put(String path, MultipartFile file) throws IOException;
    byte[] get(String path) throws IOException;
    void delete(String path) throws IOException;
    String getUrl(String path);
}
