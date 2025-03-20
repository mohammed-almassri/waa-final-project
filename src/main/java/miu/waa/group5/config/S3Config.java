package miu.waa.group5.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Value("${aws.profile}")
    private String awsProfile;

    @Value("${aws.s3.region}")
    private String region;

//    @Bean
//    public S3Client s3Client() {
//        return S3Client.builder()
//                .region(Region.of(region))
//                .credentialsProvider(ProfileCredentialsProvider.create(awsProfile))
//                .build();
//    }

    @Configuration
    @Profile("dev")
    static class LocalConfig {
        @Value("${aws.profile}")
        private String awsProfile;

        @Bean
        public S3Client s3Client(S3Config s3Config) {
            return S3Client.builder()
                    .region(Region.of(s3Config.region))
                    .credentialsProvider(ProfileCredentialsProvider.create(awsProfile))
                    .build();
        }
    }

    @Configuration
    @Profile("!dev")
    static class ProdConfig {
        @Bean
        public S3Client s3Client(S3Config s3Config) {
            return S3Client.builder()
                    .region(Region.of(s3Config.region))
                    .credentialsProvider(InstanceProfileCredentialsProvider.create())
                    .build();
        }
    }
}
