package com.example.recipeApp.service;

import com.example.recipeApp.configuration.JwtService;
import com.example.recipeApp.model.Chef;
import com.example.recipeApp.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthChefService {
    @Autowired
    ChefRepository chefRepo;
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authmanager;
    BCryptPasswordEncoder encoder;
    AuthChefService(){
        this.encoder=new BCryptPasswordEncoder(12);
    }
    public ResponseEntity<HashMap<String, String>> createTheChef(Chef requestChef) {
        HashMap<String,String> ret=new HashMap<>();
        try{

            if(requestChef.getChefName()==null || requestChef.getChefEmail()==null || requestChef.getChefPassword()==null || requestChef.getChefProfileImage()==null){
                ret.put("message","fill all the necessary fields");
                return ResponseEntity.ok(ret);
            }
            if(chefRepo.existsByChefName(requestChef.getChefName())){
                ret.put("message","UserName already exists");
                return ResponseEntity.ok(ret);
            }
            if(chefRepo.existsByChefEmail(requestChef.getChefEmail())){
                ret.put("message","email already exists");
                return ResponseEntity.ok(ret);
            }
            requestChef.setChefPassword(encoder.encode(requestChef.getChefPassword()));
            requestChef=chefRepo.save(requestChef);
            ret.put("email",requestChef.getChefEmail());
            ret.put("token",jwtService.generateToken(""+requestChef.getId()));
            return ResponseEntity.ok(ret);
        }
        catch (Exception e){
            ret.put("message","something went wrong with input please check");
            return ResponseEntity.ok(ret);
        }

    }



    public ResponseEntity<HashMap<String, String>> loginTheChef(Chef requestChef) {
        HashMap<String,String> ret=new HashMap<>();
        try{
            Chef exisitingChef=chefRepo.findByChefEmail(requestChef.getChefEmail());
            System.out.println(requestChef.getChefEmail()+" "+requestChef.getChefPassword());
            if(exisitingChef==null){
                ret.put("message","email doesnot exist");
                return ResponseEntity.ok(ret);
            }
            Authentication authObject=authmanager.authenticate(new UsernamePasswordAuthenticationToken(requestChef.getChefEmail(),requestChef.getChefPassword()));
            if(authObject.isAuthenticated()){
                ret.put("email",exisitingChef.getChefEmail());
                ret.put("token",jwtService.generateToken(""+exisitingChef.getId()));
                return ResponseEntity.ok(ret);
            }
            ret.put("message","please check the password");
            return ResponseEntity.ok(ret);
        }
        catch (Exception e){
            System.out.println(e.fillInStackTrace());
            ret.put("message","something went wrong with input please check");
            return ResponseEntity.ok(ret);
        }

    }
}
