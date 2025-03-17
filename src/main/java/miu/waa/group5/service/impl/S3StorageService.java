package miu.waa.group5.service.impl;

import miu.waa.group5.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service("s3StorageService")
public class S3StorageService implements StorageService {

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Autowired
    private CloudfrontService cloudFrontService;

    @Override
    public String put(String path, MultipartFile file) throws IOException {



        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(path)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
        return cloudFrontService.generateUrl(path);
    }

    @Override
    public byte[] get(String path) throws IOException {
        throw new UnsupportedOperationException("Direct streaming is not allowed. Use CloudFront URL.");
    }

    @Override
    public void delete(String path) throws IOException {
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(path)
                .build());
    }

    @Override
    public String getUrl(String path) {
        return cloudFrontService.generateUrl(path);
    }
}
