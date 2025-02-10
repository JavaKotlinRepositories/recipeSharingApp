package com.vishal.recipeBackend.controllers;


import com.vishal.recipeBackend.dto.RecipeDto;
import com.vishal.recipeBackend.model.Chefs;
import com.vishal.recipeBackend.service.ChefProfileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
@RequestMapping("/backend/protected")
public class ChefProfileController {


    @Autowired
    private ChefProfileService chefProfileService;

    @PostMapping("/chefInfo")
    public HashMap<String, Object> chefInfo(HttpServletRequest request) {
        Chefs chef= (Chefs) request.getAttribute("chef");
        try{
            return chefProfileService.chefInfo(chef);
        }
        catch(Exception e){
            HashMap<String, Object> response = new HashMap<>();
            response.put("message", "something went wrong with request");
            return response;
        }
    }
    @PostMapping("/createpost")
    public HashMap<String, Object> createPost(HttpServletRequest request,@RequestParam("title") String title,
                                              @RequestParam("description") String description,
                                              @RequestParam("ingredients") String ingredients,
                                              @RequestParam("instructions") String instructions,
                                              @RequestParam("postimage") MultipartFile postimage) {
        Chefs chef= (Chefs) request.getAttribute("chef");
        RecipeDto recipe= new RecipeDto();
        recipe.setTitle(title);
        recipe.setDescription(description);
        recipe.setIngredients(ingredients);
        recipe.setInstructions(instructions);
        recipe.setPostimage(postimage);
        try{
            return chefProfileService.createPost(chef,recipe);
        }
        catch(Exception e){
            HashMap<String, Object> response = new HashMap<>();
            response.put("message", "something went wrong with request");
            return response;
        }
    }
}
