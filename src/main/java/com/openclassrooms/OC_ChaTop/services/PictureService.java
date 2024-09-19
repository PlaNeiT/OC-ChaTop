package com.openclassrooms.OC_ChaTop.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * Service class for handling picture uploads to Amazon S3.
 */
@Service
public class PictureService {

    @Autowired
    private AmazonS3 s3Client;

    // Inject the bucket name from properties

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    /**
     * Uploads a file to the configured S3 bucket and returns the file's URL.
     *
     * @param file the MultipartFile to be uploaded
     * @return the URL of the uploaded file
     */
    public String uploadFile(MultipartFile file) {
        // Generate a unique filename using UUID to prevent name collisions
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Convert the MultipartFile to a standard File object
        File fileObj = convertMultiPartFileToFile(file);

        // Upload the file to the specified S3 bucket
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));

        // Delete the local file after uploading to S3
        fileObj.delete();

        // Return the URL of the uploaded file
        return s3Client.getUrl(bucketName, fileName).toString();
    }

    /**
     * Converts a MultipartFile to a standard File object.
     *
     * @param file the MultipartFile to be converted
     * @return the converted File object
     */
    private File convertMultiPartFileToFile(MultipartFile file) {
        // Create a new File object using the original filename of the uploaded file
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));

        // Write the bytes of the MultipartFile to the File object
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace(); // Log the exception if the file conversion fails
        }
        return convertedFile;
    }
}
