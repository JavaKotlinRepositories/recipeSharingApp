package com.vishal.recipeBackend.repository;

import com.vishal.recipeBackend.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Integer> {
    Likes findLikesByRecipeIdAndChefsId( int recipeId,int chefId);
    int countLikesByRecipeId(int recipeId);
}
