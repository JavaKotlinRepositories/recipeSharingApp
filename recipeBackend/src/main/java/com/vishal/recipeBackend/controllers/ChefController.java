package com.vishal.recipeBackend.controllers;

import com.vishal.recipeBackend.model.Chefs;
import com.vishal.recipeBackend.service.ChefService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/backend")
public class ChefController {
    @Autowired
    private ChefService chefService;

    @PostMapping("/login")
    public HashMap<String,Object> login(@RequestBody Chefs chefs) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("email",chefs.getEmail());
        String token=chefService.login(chefs.getEmail(),chefs.getPassword());
        map.put("token",token);
        return map;
    }

}
