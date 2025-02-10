package com.vishal.recipeBackend.service;

//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.PutObjectRequest;
import com.vishal.recipeBackend.dto.chefDto;
import com.vishal.recipeBackend.model.Chefs;
import com.vishal.recipeBackend.repository.ChefsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.HashMap;

@Service
public class ChefService {
    @Value("${aws.profilepicbucket}")
    String profilePicBucket;
    @Autowired
    JwtTokenGenerator jwtTokenGenerator;
    @Autowired
    ChefsRepository chefsRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();


    BCryptPasswordEncoder encoder;
//    AmazonS3 amazonS3;
    S3Client s3Client;;
//    public ChefService(AmazonS3 amazonS3) {
//        this.encoder=new BCryptPasswordEncoder(12);
//        this.amazonS3=amazonS3;
//    }
public ChefService(S3Client s3Client) {
    this.encoder=new BCryptPasswordEncoder(12);
    this.s3Client=s3Client;
}
    public HashMap<String, Object> login(String email, String password) {
        if(email==null || password==null) {
            return null;
        }
        Chefs chefs = chefsRepository.findByEmail(email);
        if(chefs == null) {
            return null;
        }
        Authentication auth=new UsernamePasswordAuthenticationToken(email, password, null);
        authenticationManager.authenticate(auth);
        HashMap<String,Object> hm=new HashMap<>();
        hm.put("email",chefs.getEmail());
        hm.put("token",jwtTokenGenerator.generateToken(""+chefs.getId()));
        hm.put("profilepic",preSignedUrl(chefs.getProfilepic(),profilePicBucket));
        return hm;
    }
    public HashMap<String,Object> signup(chefDto chefs) throws IOException {
        HashMap<String,Object> hm=new HashMap<>();

        if(chefs.getEmail()==null) {
            hm.put("message","please enter email");
            return hm;
        }
        if(chefs.getPassword()==null) {
            hm.put("message", "please enter password");
            return hm;
        }
        if(chefs.getFirstName()==null) {
            hm.put("message","please enter first name");
            return hm;
        }
        if(chefs.getLastName()==null) {
            hm.put("message","please enter last name");
            return hm;
        }
        if(chefs.getProfilepic()==null) {
            hm.put("message","please enter profilepic");
            return hm;
        }
        System.out.println(chefs.getProfilepic());
        Chefs existingchef=chefsRepository.findByEmail(chefs.getEmail());
        if(existingchef!=null) {
            hm.put("message","This email already in use");
            return hm;
        }

//        amazonS3.putObject(new PutObjectRequest(profilePicBucket,chefs.getProfilepic().getName(), convertMultiPartToFile(chefs.getProfilepic())));
        if(!isJpeg(chefs.getProfilepic())) {
            hm.put("message","provide a valid jpeg image");
            return hm;
        }
        String filename=generateRandomName(32);

        try{

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(profilePicBucket)
                    .key(filename+".jpeg")
                    .build();

            PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(chefs.getProfilepic().getBytes()));
        }
        catch(Exception e){
            hm.put("message",e.getMessage());
            return hm;
        }


        Chefs newchefs=new Chefs();
        newchefs.setEmail(chefs.getEmail());
        newchefs.setFirstName(chefs.getFirstName());
        newchefs.setLastName(chefs.getLastName());
        newchefs.setPassword(encoder.encode(chefs.getPassword()));
        newchefs.setProfilepic(filename);
        Chefs newchef=chefsRepository.save(newchefs);

        hm.put("email",newchef.getEmail());
        hm.put("token",jwtTokenGenerator.generateToken(""+newchef.getId()));
        hm.put("profilepic",preSignedUrl(filename,profilePicBucket));
        return hm;
    }


    public boolean isJpeg(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            byte[] header = new byte[2];
            if (inputStream.read(header) != 2) {
                return false;
            }
            return (header[0] & 0xFF) == 0xFF && (header[1] & 0xFF) == 0xD8;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static String generateRandomName(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
    public String preSignedUrl(String filename,String bucket){
    System.out.println(filename+" "+bucket);
        try (S3Presigner presigner = S3Presigner.create()) {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(filename+".jpeg")
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(60*24))
                    .getObjectRequest(getObjectRequest)
                    .build();

            PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);
            return presignedRequest.url().toString();

        }
        catch (Exception e){

            return e.getMessage();
        }
    }
}
