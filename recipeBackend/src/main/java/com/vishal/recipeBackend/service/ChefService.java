package com.vishal.recipeBackend.service;

import com.vishal.recipeBackend.model.Chefs;
import com.vishal.recipeBackend.repository.ChefsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
public class ChefService {
    @Autowired
    JwtTokenGenerator jwtTokenGenerator;
    @Autowired
    ChefsRepository chefsRepository;
    @Autowired
    AuthenticationManager authenticationManager;

    public String login(String email,String password) {
        if(email==null || password==null) {
            return null;
        }
        Chefs chefs = chefsRepository.findByEmail(email);
        if(chefs == null) {
            return null;
        }
        Authentication auth=new UsernamePasswordAuthenticationToken(email, password, null);
        authenticationManager.authenticate(auth);
            return jwtTokenGenerator.generateToken(""+chefs.getId());

    }
}
