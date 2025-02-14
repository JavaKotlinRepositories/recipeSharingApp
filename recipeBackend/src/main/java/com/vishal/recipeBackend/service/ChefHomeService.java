package com.vishal.recipeBackend.service;

import com.vishal.recipeBackend.dto.RecipeDto;
import com.vishal.recipeBackend.model.Chefs;
import com.vishal.recipeBackend.model.Likes;
import com.vishal.recipeBackend.model.Recipe;
import com.vishal.recipeBackend.repository.LikesRepository;
import com.vishal.recipeBackend.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ChefHomeService {
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    LikesRepository likesRepository;
    @Autowired
    FileUtilityService fileUtilityService;
    @Value("${aws.profilepicbucket}")
    String profilePicBucket;
    @Value("${aws.postimagesbucket}")
    String postImagesBucket;

    public  List<RecipeDto> getPostinfo(Chefs chef, int num1, int num2){
        int pageSize = num2 - num1;  // Define the number of records to fetch
        Pageable pageable = PageRequest.of(num1 / pageSize, pageSize);
        List<Recipe>  recipes=recipeRepository.findAllByOrderByCreatedAtDesc( pageable).getContent();
        List<RecipeDto> recipeDtos=new ArrayList<>();
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
        for(int i=0;i<recipes.size();i++){

            RecipeDto recipeDto=new RecipeDto();
            recipeDto.setId(recipes.get(i).getId());
            recipeDto.setTitle(recipes.get(i).getTitle());
            recipeDto.setDescription(recipes.get(i).getDescription());
            recipeDto.setIngredients(recipes.get(i).getIngredients());
            recipeDto.setInstructions(recipes.get(i).getInstructions());
            recipeDto.setChef(recipes.get(i).getChef());
            recipeDto.setImage(recipes.get(i).getImage());
            recipeDto.setCreatedAt(recipes.get(i).getCreatedAt());
            Likes liked=likesRepository.findLikesByRecipeIdAndChefsId(recipes.get(i).getId(), chef.getId());
            int count=likesRepository.countLikesByRecipeId(recipes.get(i).getId());
            if(liked!=null){
                recipeDto.setIsLiked(true);
            }
            else{
                recipeDto.setIsLiked(false);
            }
            recipeDto.setLikes(count);

            recipeDtos.add(recipeDto);
        }

        return recipeDtos;
    }

    public HashMap<String, String> likePost(Chefs chef, int id1) {
        Likes likes=new Likes();
        likes.setChefs(chef);
        Recipe recipe=recipeRepository.findById(id1).get();
        if(recipe==null){
            throw new RuntimeException("Recipe not found");
        }
        likes.setRecipe(recipe);
        likesRepository.save(likes);
        HashMap<String, String> map=new HashMap<>();
        map.put("message","post was liked");
        return map;
    }
    public HashMap<String, String> unlikePost(Chefs chef, int id1) {
        Recipe recipe=recipeRepository.findById(id1).get();

        if(recipe==null){
            throw new RuntimeException("Recipe not found");
        }
        Likes likes=likesRepository.findLikesByRecipeIdAndChefsId(recipe.getId(),chef.getId());
        likesRepository.delete(likes);
        HashMap<String, String> map=new HashMap<>();
        map.put("message","like was removed");
        return map;
    }


}
