package com.vishal.recipeBackend.controllers;


import com.vishal.recipeBackend.dto.chefDto;
import com.vishal.recipeBackend.model.Chefs;
import com.vishal.recipeBackend.service.ChefService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
@RequestMapping("/backend")
public class ChefController {
    private final ChefService chefService;
    ChefController(ChefService chefService){
        this.chefService = chefService;
    }
    @PostMapping("/login")
    public HashMap<String,Object> login(@RequestBody Chefs chefs) {
        HashMap<String,Object> map=chefService.login(chefs.getEmail(),chefs.getPassword());
        if(map==null){
            map=new HashMap<>();

            map.put("status","please provide a valid email and password");
            return map;
        }
        return map;
    }

    @PostMapping("/signup")
    public HashMap<String,Object> signup( @RequestParam("firstName") String firstName,
                                          @RequestParam("lastName") String lastName,
                                          @RequestParam("password") String password,
                                          @RequestParam("email") String email,
                                          @RequestParam("profilepic") MultipartFile profilePic) {
        chefDto dto=new chefDto();
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setPassword(password);
        dto.setEmail(email);
        dto.setProfilepic(profilePic);
        HashMap<String,Object> map = chefService.signup(dto);
        if(map==null){
            map=new HashMap<>();
            map.put("status","please provide valid data");
            return map;
        }
        return map;
    }
}
