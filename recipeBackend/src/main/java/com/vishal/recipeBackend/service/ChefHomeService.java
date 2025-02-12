package com.vishal.recipeBackend.service;

import com.vishal.recipeBackend.model.Chefs;
import com.vishal.recipeBackend.model.Recipe;
import com.vishal.recipeBackend.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ChefHomeService {
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    FileUtilityService fileUtilityService;
    @Value("${aws.profilepicbucket}")
    String profilePicBucket;
    @Value("${aws.postimagesbucket}")
    String postImagesBucket;

    public  List<Recipe> getPostinfo(Chefs chef, int num1, int num2){
        int pageSize = num2 - num1;  // Define the number of records to fetch
        Pageable pageable = PageRequest.of(num1 / pageSize, pageSize);
        List<Recipe>  recipes=recipeRepository.findAllByOrderByCreatedAtDesc( pageable).getContent();
//        Chefs newchef=new Chefs();
//        newchef.setId(chef.getId());
//        newchef.setFirstName(chef.getFirstName());
//        newchef.setLastName(chef.getLastName());
//        newchef.setProfilepic(fileUtilityService.preSignedUrl(chef.getProfilepic(),profilePicBucket));
        for(int i=0;i<recipes.size();i++){
            Chefs chef1=recipes.get(i).getChef();
            if(chef1.getEmail()!=null){
                chef1.setProfilepic(fileUtilityService.preSignedUrl(chef1.getProfilepic(),profilePicBucket));
                chef1.setPassword(null);
                chef1.setEmail(null);
                recipes.get(i).setChef(chef1);
            }

            recipes.get(i).setImage(fileUtilityService.preSignedUrl(recipes.get(i).getImage(),postImagesBucket));
        }
        return recipes;
    }
}
