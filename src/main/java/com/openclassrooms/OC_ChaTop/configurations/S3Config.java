package com.openclassrooms.OC_ChaTop.configurations;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * S3Config is a configuration class that sets up the Amazon S3 client
 * with the necessary AWS credentials and region settings.
 */
@Configuration
public class S3Config {

    // Inject AWS access key from application properties
    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    // Inject AWS secret key from application properties
    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    // Inject AWS region from application properties
    @Value("${cloud.aws.region.static}")
    private String region;

    /**
     * Configures and creates an Amazon S3 client bean.
     * The client is configured using AWS credentials and region specified
     * in the application properties file.
     *
     * @return an AmazonS3 client instance configured with AWS credentials
     */
    @Bean
    public AmazonS3 s3Client() {
        // Create AWS credentials using access key and secret key
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        // Build and return the Amazon S3 client with specified credentials and region
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
