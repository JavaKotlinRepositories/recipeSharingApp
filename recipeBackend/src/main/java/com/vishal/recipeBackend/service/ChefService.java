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
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.security.SecureRandom;
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
    @Autowired
    FileUtilityService fileUtilityService;


    BCryptPasswordEncoder encoder;
//    AmazonS3 amazonS3;
    @Autowired
    S3Client s3Client;;
//    public ChefService(AmazonS3 amazonS3) {
//        this.encoder=new BCryptPasswordEncoder(12);
//        this.amazonS3=amazonS3;
//    }
public ChefService() {
    this.encoder=new BCryptPasswordEncoder(12);
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
        hm.put("profilepic",fileUtilityService.preSignedUrl(chefs.getProfilepic(),profilePicBucket));
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
        Chefs existingchef=chefsRepository.findByEmail(chefs.getEmail());
        if(existingchef!=null) {
            hm.put("message","This email already in use");
            return hm;
        }

//        amazonS3.putObject(new PutObjectRequest(profilePicBucket,chefs.getProfilepic().getName(), convertMultiPartToFile(chefs.getProfilepic())));
        if(!fileUtilityService.isJpeg(chefs.getProfilepic())) {
            hm.put("message","provide a valid jpeg image");
            return hm;
        }
        String filename=fileUtilityService.generateRandomName(32);

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
        hm.put("profilepic",fileUtilityService.preSignedUrl(filename,profilePicBucket));
        return hm;
    }



}
