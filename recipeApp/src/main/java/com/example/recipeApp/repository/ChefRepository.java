package com.example.recipeApp.repository;

import com.example.recipeApp.model.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefRepository extends JpaRepository<Chef,Long> {
    Chef findByChefName(String name);
    // Check if either chefName or chefEmail exists
    boolean existsByChefEmail(String chefEmail);
    boolean existsByChefName(String chefName);

    Chef findByChefEmail(String chefEmail);
}
