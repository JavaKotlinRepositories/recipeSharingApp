package com.example.recipeApp.service;

import com.example.recipeApp.model.Chef;
import com.example.recipeApp.model.Recipe;
import com.example.recipeApp.repository.ChefRepository;
import com.example.recipeApp.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    ChefRepository chefRepo;
    @Autowired
    RecipeRepository recipeRepo;

    public ResponseEntity<HashMap<String, Object>> getAllRecipes(Long userid) {
        HashMap<String,Object> ret=new HashMap<>();
        ret.put("recipes",recipeRepo.findByChefId(userid));
        return ResponseEntity.ok(ret);
    }


    public ResponseEntity<HashMap<String, Object>> createRecipe(Long userid,Recipe recipe) {
        Optional<Chef> chef=chefRepo.findById(userid);
        HashMap<String,Object> ret=new HashMap<>();
        if(chef.isPresent()){
            recipe.setChef(chef.get());
            ret.put("recipe",recipeRepo.save(recipe));
            return ResponseEntity.ok(ret);
        }
        else{
            ret.put("message","chef not found");
            return ResponseEntity.ok(ret);
        }
    }


}
