package com.vishal.recipeBackend.repository;

import com.vishal.recipeBackend.model.Chefs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefsRepository extends JpaRepository<Chefs,Integer> {
    Chefs findByEmail(String email);
}
