package com.example.recipeApp.repository;

import com.example.recipeApp.model.Comment;
import com.example.recipeApp.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

//    @Query("""
//    SELECT r
//    FROM Recipe r
//    WHERE r.id NOT IN (
//        SELECT c.recipe.id
//        FROM Comment c
//        WHERE c.chef.id = :userId
//    )
//    ORDER BY r.viewCount DESC
//    """)
//    List<Recipe> findTopUnviewedPostsByUser(@Param("userId") Long userId, Pageable pageable);
}

