package com.vishal.recipeBackend.controllers;


import com.vishal.recipeBackend.model.Chefs;
import com.vishal.recipeBackend.service.ChefProfileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/backend/protected")
public class ChefProfileController {


    @Autowired
    private ChefProfileService chefProfileService;

    @PostMapping("/chefInfo")
    public HashMap<String, Object> chefInfo(HttpServletRequest request) {
        Chefs chef= (Chefs) request.getAttribute("chef");

        return chefProfileService.chefInfo(chef);
    }
}
