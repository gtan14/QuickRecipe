package com.example.quickrecipe;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Gerald on 4/13/2018.
 */

public class Recipe{

    private ArrayList<String> ingredientList;
    private String recipeName, prepTime, cookTime, instructions;
    private transient Drawable recipeImage;

    public Recipe(){

    }

    public Recipe(String recipeName, String prepTime, String cookTime, String instructions, ArrayList<String> ingredientList){
        this.cookTime = cookTime;
        this.prepTime = prepTime;
        this.recipeName = recipeName;
        this.instructions = instructions;
        this.ingredientList = ingredientList;
    }

    public void setIngredientList(ArrayList<String> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public ArrayList<String> getIngredientList() {
        return ingredientList;
    }

    public Drawable getRecipeImage() {
        return recipeImage;
    }

    public String getCookTime() {
        return cookTime;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public void setRecipeImage(Drawable recipeImage) {
        this.recipeImage = recipeImage;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
}
