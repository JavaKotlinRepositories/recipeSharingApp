package com.example.recipeApp.repository;

import com.example.recipeApp.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,Long> {
    public List<Recipe> findByChefId(Long chef_id);
}
