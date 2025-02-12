package com.vishal.recipeBackend.controllers;


import com.vishal.recipeBackend.dto.RecipeDto;
import com.vishal.recipeBackend.model.Chefs;
import com.vishal.recipeBackend.model.Recipe;
import com.vishal.recipeBackend.service.ChefProfileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    @PostMapping("/getpostinfo")
    public List<Recipe> getPostinfo(HttpServletRequest request, @RequestBody HashMap<String, Object> postinfo) {

        try{
            if(postinfo.get("num1") ==null || postinfo.get("num2") ==null){
                return new ArrayList<>();
            }
            int num1 = (int) postinfo.get("num1");
            int num2 = (int) postinfo.get("num2");
            Chefs chef= (Chefs) request.getAttribute("chef");
            return chefProfileService.getPostinfo(chef,num1,num2+1);
        }
        catch(Exception e){
            return new ArrayList<>();
        }
    }

    @DeleteMapping("/deletepost/{id}")
    public HashMap<String,String> deletePost(HttpServletRequest request, @PathVariable int id) {
        Chefs chef = (Chefs) request.getAttribute("chef");
        HashMap<String, String> response = new HashMap<>();

        try{
            chefProfileService.deletePost(chef, id,response);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println(e.toString());
            System.out.println(e.getMessage());
            response.put("message", "something went wrong with request deletion did not happen");
        }
        return response;

    }
}
