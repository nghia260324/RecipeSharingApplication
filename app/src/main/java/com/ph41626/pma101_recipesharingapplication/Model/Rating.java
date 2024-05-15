package com.ph41626.pma101_recipesharingapplication.Model;

public class Rating {
    private String id;
    private String recipeId;
    private String userId;
    private float ratingValue;

    public Rating() {
    }

    public Rating(String id, String recipeId, String userId, float ratingValue) {
        this.id = id;
        this.recipeId = recipeId;
        this.userId = userId;
        this.ratingValue = ratingValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(float ratingValue) {
        this.ratingValue = ratingValue;
    }
}
