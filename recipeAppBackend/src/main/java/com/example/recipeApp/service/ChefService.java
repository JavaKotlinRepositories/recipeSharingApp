package com.example.recipeApp.service;

import com.example.recipeApp.model.Chef;
import com.example.recipeApp.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;


@Service
public class ChefService {

    @Autowired
    ChefRepository chefRepo;
    public ResponseEntity<HashMap<String, Object>> getUserInfo(Long userId) {
        HashMap<String,Object> ret=new HashMap<>();
        try{
            Optional<Chef> chefopt=chefRepo.findById(userId);
            if(chefopt.isPresent()){
                Chef chef=chefopt.get();
                ret.put("chefName",chef.getChefName());
                ret.put("chefEmail",chef.getChefEmail());
                ret.put("chefProfileImage",chef.getChefProfileImage());
                return ResponseEntity.ok(ret);
            }
            else{
                ret.put("message","chef not found");
                return ResponseEntity.ok(ret);
            }
        }
        catch (Exception e){
            ret.put("message","something went wrong with server");
            return ResponseEntity.ok(ret);
        }
    }
}
