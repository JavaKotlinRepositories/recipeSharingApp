package com.vishal.recipeBackend.repository;

import com.vishal.recipeBackend.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    Page<Recipe> findAllByOrderByCreatedAtDesc(Pageable pageable);
    @Modifying
    @Transactional
    @Query("DELETE FROM Recipe r WHERE r.id = :id AND r.chef.id = :chefId")
    int deleteByIdAndChefId(@Param("id") Integer id, @Param("chefId") Integer chefId);
}



