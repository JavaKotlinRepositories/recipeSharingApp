package com.vishal.recipeBackend.service;

//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.client.builder.AwsClientBuilder;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Service
public class S3service {

    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.secretAccessKey}")
    private String secretAccessKey;

//    @Bean
//    public AmazonS3 S3client(){
//
//        BasicAWSCredentials awsCredentials=new BasicAWSCredentials(accessKeyId, secretAccessKey);
//        return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).withRegion(Regions.US_EAST_1).build();
//    }


    @Bean
    public S3Client  S3client(){
//        System.setProperty("aws.accessKeyId", accessKeyId);
//        System.setProperty("aws.secretAccessKey", secretAccessKey);
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();

        Region region = Region.US_EAST_1;
        return S3Client.builder()
                .region(region)
                .credentialsProvider(credentialsProvider)
                .build();
    }
}
