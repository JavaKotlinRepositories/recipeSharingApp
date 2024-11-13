package com.example.recipeApp.controller;

import com.example.recipeApp.model.Comment;
import com.example.recipeApp.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;

@RestController
public class CommentController {
    @Autowired
    CommentService commServ;

    @GetMapping("/comment/{id}")
    public ResponseEntity<HashMap<String, Object>> getCommentForPost(HttpServletRequest request, @PathVariable long id){
        try{
            return commServ.getCommentForPost(id);
        }
        catch (Exception e){
            HashMap<String,Object> ret=new HashMap<>();
            ret.put("message","something went wrong with server");
            return ResponseEntity.ok(ret);
        }
    }



    @PostMapping("/comment/{id}")
    public ResponseEntity<HashMap<String,Object>> createComments(HttpServletRequest request, @RequestBody Comment comment,@PathVariable long id){
        try{
            return commServ.createComments((Long)request.getAttribute("userId"),id,comment);
        }
        catch (Exception e){
            HashMap<String,Object> ret=new HashMap<>();
            ret.put("message","something went wrong with server");
            return ResponseEntity.ok(ret);
        }
    }

}
