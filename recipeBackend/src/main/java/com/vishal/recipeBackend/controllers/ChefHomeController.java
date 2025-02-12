package com.vishal.recipeBackend.controllers;

import com.vishal.recipeBackend.model.Chefs;
import com.vishal.recipeBackend.model.Recipe;
import com.vishal.recipeBackend.service.ChefHomeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/backend/protected")
public class ChefHomeController {
    @Autowired
    private ChefHomeService chefHomeService;


    @PostMapping("/fetchrecipes")
    public List<Recipe> fetchRecipes(HttpServletRequest request, @RequestBody HashMap<String, Object> postinfo) {

        try{
            if(postinfo.get("num1") ==null || postinfo.get("num2") ==null){
                return new ArrayList<>();
            }
            int num1 = (int) postinfo.get("num1");
            int num2 = (int) postinfo.get("num2");
            Chefs chef= (Chefs) request.getAttribute("chef");
            return chefHomeService.getPostinfo(chef,num1,num2+1);
        }
        catch(Exception e){
            return new ArrayList<>();
        }
    }
}
