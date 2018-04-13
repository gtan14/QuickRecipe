package com.example.quickrecipe;

import android.widget.ImageView;

/**
 * Created by Gerald on 4/11/2018.
 */

public class Cart {
    private int categoryPosition;
    private String ingredient;
    private Integer ingredientImg;

    public Cart(int category, String ingredient, Integer ingredientImg){
        this.categoryPosition = category;
        this.ingredient = ingredient;
        this.ingredientImg = ingredientImg;
    }

    public Cart(){

    }

    public int getCategory() {
        return categoryPosition;
    }

    public void setCategory(int category) {
        this.categoryPosition = category;
    }

    public Integer getIngredientImg() {
        return ingredientImg;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setIngredientImg(Integer ingredientImg) {
        this.ingredientImg = ingredientImg;
    }
}
