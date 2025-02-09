package com.vishal.recipeBackend.service;

import com.vishal.recipeBackend.dto.chefDto;
import com.vishal.recipeBackend.model.Chefs;
import com.vishal.recipeBackend.repository.ChefsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;

@Service
public class ChefService {
    @Autowired
    JwtTokenGenerator jwtTokenGenerator;
    @Autowired
    ChefsRepository chefsRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    BCryptPasswordEncoder encoder;

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
        hm.put("profilepic",chefs.getProfilepic());
        return hm;
    }
    public HashMap<String,Object> signup(chefDto chefs) {
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
        Chefs newchefs=new Chefs();
        newchefs.setEmail(chefs.getEmail());
        newchefs.setFirstName(chefs.getFirstName());
        newchefs.setLastName(chefs.getLastName());
        newchefs.setPassword(encoder.encode(chefs.getPassword()));
        newchefs.setProfilepic("asdf");
        Chefs newchef=chefsRepository.save(newchefs);
        hm.put("email",newchef.getEmail());
        hm.put("token",jwtTokenGenerator.generateToken(""+newchef.getId()));
        hm.put("profilepic",newchef.getProfilepic());
        return hm;
    }
}
