package com.vishal.recipeBackend.dto;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public class RecipeDto {
   private String title;
    private  String description;
    private  String ingredients;
    private String instructions;

    private MultipartFile postimage;

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
