package com.vishal.recipeBackend.controllers;

import com.vishal.recipeBackend.dto.RecipeDto;
import com.vishal.recipeBackend.model.Chefs;
import com.vishal.recipeBackend.model.Recipe;
import com.vishal.recipeBackend.service.ChefHomeService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public List<RecipeDto> fetchRecipes(HttpServletRequest request, @RequestBody HashMap<String, Object> postinfo) {
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

    @PostMapping("/likepost")
    public ResponseEntity<HashMap<String,String>> likePost(HttpServletRequest request, @RequestBody HashMap<String, Object> likeinfo) {
        HashMap<String,String> ret = new HashMap<>();
        try{
            if(likeinfo.get("id") ==null ){
                ret.put("message", "Invalid ID");
                return ResponseEntity.status(400).body(ret);
            }
            int id1 = (int) likeinfo.get("id");
            Chefs chef= (Chefs) request.getAttribute("chef");
            return ResponseEntity.ok(chefHomeService.likePost(chef,id1));
        }
        catch (Exception e){
            ret.put("message", "Invalid ID");
            
            return ResponseEntity.status(400).body(ret);
        }
    }

    @PostMapping("/unlikepost")
    public ResponseEntity<HashMap<String,String>> unlikePost(HttpServletRequest request, @RequestBody HashMap<String, Object> likeinfo) {
        HashMap<String,String> ret = new HashMap<>();
        try{
            if(likeinfo.get("id") ==null ){
                ret.put("message", "Invalid ID");
                return ResponseEntity.status(400).body(ret);
            }
            int id1 = (int) likeinfo.get("id");
            Chefs chef= (Chefs) request.getAttribute("chef");
            return ResponseEntity.ok(chefHomeService.unlikePost(chef,id1));
        }
        catch (Exception e){
            ret.put("message", "Invalid ID");
            return ResponseEntity.status(400).body(ret);
        }
    }
}
