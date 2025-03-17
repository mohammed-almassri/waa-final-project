package miu.waa.group5.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudFrontConfig {

    @Value("${cloudfront.domain-name}")
    private String cloudFrontDomain;

    @Bean
    public String cloudFrontDomain() {
        return cloudFrontDomain;
    }
}