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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ChefProfileService {

    @Autowired
    private FileUtilityService fileUtilityService;

    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    LikesRepository likesRepository;

    @Value("${aws.profilepicbucket}")
    String profilePicBucket;
    @Value("${aws.postimagesbucket}")
    String postImagesBucket;
    public HashMap<String, Object> chefInfo(Chefs chef) {
        HashMap<String,Object> hm = new HashMap<>();
        hm.put("id",chef.getId());
        hm.put("firstName",chef.getFirstName());
        hm.put("lastName",chef.getLastName());
        hm.put("profilepic",fileUtilityService.preSignedUrl(chef.getProfilepic(),profilePicBucket));

        return hm;
    }

    public HashMap<String, Object> createPost(Chefs chef, RecipeDto recipe) {
        HashMap<String,Object> hm = new HashMap<>();
        HashMap<String,Object> chefhm=new HashMap<>();
        Recipe recipe1 = new Recipe();
        if(recipe.getTitle()==null || recipe.getTitle().equals("")){
            hm.put("message","post title not found");
        }
        if(recipe.getDescription()==null || recipe.getDescription().equals("")){
            hm.put("message","post description not found");
        }
        if(recipe.getIngredients()==null || recipe.getIngredients().equals("")){
            hm.put("message","post ingredients not found");
        }
        if(recipe.getInstructions()==null || recipe.getInstructions().equals("")){
            hm.put("message","post instructions not found");
        }
        if(recipe.getPostimage()==null || recipe.getPostimage().equals("")){
            hm.put("message","postimage not found");
        }
        if(!hm.isEmpty()){
            return hm;
        }
        String filename=fileUtilityService.generateRandomName(32);
        recipe1.setTitle(recipe.getTitle());
        recipe1.setDescription(recipe.getDescription());
        recipe1.setIngredients(recipe.getIngredients());
        recipe1.setInstructions(recipe.getInstructions());
        recipe1.setChef(chef);
        recipe1.setImage(filename);
        boolean fileuploaded =fileUtilityService.checkJpegAndPutObject(postImagesBucket, filename, recipe.getPostimage(),  hm);
        if(!fileuploaded){
            return hm;
        }
        recipe1=recipeRepository.save(recipe1);
        String image=fileUtilityService.preSignedUrl(filename,postImagesBucket);
        hm.put("id",recipe1.getId());
        hm.put("title",recipe1.getTitle());
        hm.put("description",recipe1.getDescription());
        hm.put("ingredients",recipe1.getIngredients());
        hm.put("instructions",recipe1.getInstructions());
        hm.put("image",image);
        hm.put("chefId",recipe1.getChef().getId());
        chefhm.put("id",chef.getId());
        chefhm.put("firstName",chef.getFirstName());
        chefhm.put("lastName",chef.getLastName());
        chefhm.put("profilepic",fileUtilityService.preSignedUrl(chef.getProfilepic(),profilePicBucket));
        hm.put("chef",chefhm);
        return hm;
    }

    public List<RecipeDto> getPostinfo(Chefs chef,  int num1, int num2) {
        int pageSize = num2 - num1;  // Define the number of records to fetch
        Pageable pageable = PageRequest.of(num1 / pageSize, pageSize);
            List<Recipe>  recipes=recipeRepository.findAllByChefIdOrderByCreatedAtDesc(chef.getId(), pageable).getContent();
        List<RecipeDto>  recipeDtos=new ArrayList<>();
            Chefs newchef=new Chefs();
            newchef.setId(chef.getId());
            newchef.setFirstName(chef.getFirstName());
            newchef.setLastName(chef.getLastName());
            newchef.setProfilepic(fileUtilityService.preSignedUrl(chef.getProfilepic(),profilePicBucket));

            for(int i=0;i<recipes.size();i++){
                recipes.get(i).setChef(newchef);
                recipes.get(i).setImage(fileUtilityService.preSignedUrl(recipes.get(i).getImage(),postImagesBucket));
            }
            for(int i=0;i<recipes.size();i++){
                RecipeDto recipeDto=new RecipeDto();
                recipeDto.setId(recipes.get(i).getId());
                recipeDto.setTitle(recipes.get(i).getTitle());
                recipeDto.setDescription(recipes.get(i).getDescription());
                recipeDto.setIngredients(recipes.get(i).getIngredients());
                recipeDto.setInstructions(recipes.get(i).getInstructions());
                recipeDto.setChef(newchef);
                recipeDto.setImage(recipes.get(i).getImage());
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

    @Transactional
    public void deletePost(Chefs chef,  Integer id,HashMap<String,String> hm) {
        likesRepository.deleteAllByRecipeId(id);
        int deletecount=recipeRepository.deleteByIdAndChefId(id,chef.getId());

        if(deletecount>0){
            hm.put("message","post deleted successfully");
        }
        else{
            hm.put("message","post deletion failed");
        }
    }

}
