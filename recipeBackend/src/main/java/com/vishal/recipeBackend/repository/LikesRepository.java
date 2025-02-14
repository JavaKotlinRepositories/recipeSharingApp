package com.vishal.recipeBackend.repository;

import com.vishal.recipeBackend.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Integer> {
    Likes findLikesByRecipeIdAndChefsId( int recipeId,int chefId);
    int countLikesByRecipeId(int recipeId);
    @Modifying
    @Query("DELETE FROM Likes r WHERE r.recipe.id = :recipeId")
    int deleteAllByRecipeId(@Param("recipeId") int recipeId);
}
