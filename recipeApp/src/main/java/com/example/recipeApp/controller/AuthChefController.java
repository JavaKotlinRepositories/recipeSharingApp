package com.example.recipeApp.controller;

import com.example.recipeApp.model.Chef;
import com.example.recipeApp.service.AuthChefService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class AuthChefController {
    AuthChefService authChefService;
    AuthChefController(AuthChefService authChefService){
        this.authChefService=authChefService;
    }
    @PostMapping("/signup")
    public ResponseEntity<HashMap<String,String>> signupChef(@RequestBody Chef chef){
        return authChefService.createTheChef(chef);
    }

    @PostMapping("/login")
    public ResponseEntity<HashMap<String,String>> loginChef(@RequestBody Chef chef){
        return authChefService.loginTheChef(chef);
    }
}
