package miu.waa.group5.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CloudfrontService {

    @Value("${cloudfront.domain-name}")
    private String domainName;

    public String generateUrl(String fileName) {
        return String.format("https://%s/%s", domainName, fileName);
    }
}