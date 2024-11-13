package com.example.recipeApp.service;

import com.example.recipeApp.model.Chef;
import com.example.recipeApp.model.Comment;
import com.example.recipeApp.model.Recipe;
import com.example.recipeApp.repository.ChefRepository;
import com.example.recipeApp.repository.CommentRepository;
import com.example.recipeApp.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Optional;


@Service
public class CommentService {

    @Autowired
    ChefRepository chefRepo;
    @Autowired
    CommentRepository commentRepo;
    @Autowired
    RecipeRepository recipeRepo;


    public ResponseEntity<HashMap<String, Object>> getCommentForPost(Long postId) {
        HashMap<String,Object> ret=new HashMap<>();

        return ResponseEntity.ok(ret);
    }




    public ResponseEntity<HashMap<String, Object>> createComments(Long userId,Long postId,Comment comment) {
        HashMap<String,Object> ret=new HashMap<>();
        Optional<Chef> chef=chefRepo.findById(userId);
        if(chef.isPresent()){
            Optional<Recipe> recipe=recipeRepo.findById(postId);
            if(recipe.isPresent()){
                comment.setChef(chef.get());
                comment.setRecipe(recipe.get());
                ret.put("comment",commentRepo.save(comment));
            }
            else{
                ret.put("message","chef does not exist");
            }
        }
        else{
            ret.put("message","chef does not exist");
        }
        return ResponseEntity.ok(ret);
    }


}
