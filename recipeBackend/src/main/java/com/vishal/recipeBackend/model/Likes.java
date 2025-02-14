package com.vishal.recipeBackend.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "likes", uniqueConstraints = {@UniqueConstraint(columnNames = {"chefId", "recipeId"})})
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "chefId",nullable = false)
    private Chefs chefs;

    @ManyToOne
    @JoinColumn(name = "recipeId",nullable = false)
    private Recipe recipe;
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Chefs getChefs() {
        return chefs;
    }

    public void setChefs(Chefs chefs) {
        this.chefs = chefs;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
