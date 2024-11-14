package com.example.recipeApp.controller;

import com.example.recipeApp.model.Recipe;
import com.example.recipeApp.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class RecipeController {

    @Autowired
    RecipeService recService;

    @GetMapping("/recipe")
    public ResponseEntity<HashMap<String,Object>> getAllRecipes(HttpServletRequest request){
        try{
            return recService.getAllRecipes((Long) request.getAttribute("userId"));
        }
        catch (Exception e){
            HashMap<String,Object> ret=new HashMap<>();
            ret.put("message","Something went wrong with server");
            return ResponseEntity.ok(ret);
        }
    }



    @PostMapping("/recipe")
    public ResponseEntity<HashMap<String,Object>> createRecipe(HttpServletRequest request,@RequestBody Recipe recipe){
        try{
            return recService.createRecipe((Long) request.getAttribute("userId"),recipe);
        }
        catch (Exception e){
            HashMap<String,Object> ret=new HashMap<>();
            ret.put("message","Something went wrong with server");
            return ResponseEntity.ok(ret);
        }
    }
}
