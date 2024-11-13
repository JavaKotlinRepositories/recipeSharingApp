package com.example.recipeApp.model;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Chef {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @NotNull
    @Column(unique = true,nullable = false)
    String chefName;
    @NotNull
    @Column(unique = true,nullable = false)
    String chefEmail;
    @NotNull
    @Column(nullable = false)
    String chefPassword;
    @NotNull
    @Column(unique = true,nullable = false)
    String chefProfileImage;

    @OneToMany(mappedBy = "chef", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Recipe> recipes = new ArrayList<>();

    @OneToMany(mappedBy = "chef", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment>  comments= new ArrayList<>();

    public String getChefEmail() {
        return chefEmail;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void setChefEmail(String chefEmail) {
        this.chefEmail = chefEmail;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChefPassword() {
        return chefPassword;
    }

    public void setChefPassword(String chefPassword) {
        this.chefPassword = chefPassword;
    }

    public String getChefProfileImage() {
        return chefProfileImage;
    }

    public void setChefProfileImage(String chefProfileImage) {
        this.chefProfileImage = chefProfileImage;
    }

    public String getChefName() {
        return chefName;
    }

    public void setChefName(String chefName) {
        this.chefName = chefName;
    }
}
