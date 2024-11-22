package com.example.recipeApp.controller;

import com.example.recipeApp.model.Chef;
import com.example.recipeApp.service.ChefService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class ChefController {
    @Autowired
    ChefService chefService;
    @GetMapping("/userprofileinfo")
    public ResponseEntity<HashMap<String,Object>> getUserInfo(HttpServletRequest request){
        return chefService.getUserInfo((Long)request.getAttribute("userId"));
    }
}
