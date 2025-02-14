package com.vishal.recipeBackend.dto;

import com.vishal.recipeBackend.model.Chefs;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

public class RecipeDto {
    private Integer id;
   private String title;
    private  String description;



    private  String ingredients;
    private String instructions;

    private MultipartFile postimage;
    private Instant createdAt;
    private String image;
    private Chefs chef;
    private Integer likes;
    private Boolean isLiked;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Chefs getChef() {
        return chef;
    }

    public void setChef(Chefs chef) {
        this.chef = chef;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public MultipartFile getPostimage() {
        return postimage;
    }

    public void setPostimage(MultipartFile postimage) {
        this.postimage = postimage;
    }

    @Override
    public String toString() {
        return "RecipeDto{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", instructions='" + instructions + '\'' +
                ", postimage=" + postimage +
                '}';
    }
}
