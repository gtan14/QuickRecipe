package com.example.quickrecipe;

import java.util.ArrayList;

/**
 * Created by Gerald on 4/17/2018.
 */

public class SearchModel {

    private String[] ingredients;
    private int[] imgs;
    private ArrayList<String> ingredientChecked;

    public void setIngredientChecked(ArrayList<String> ingredientChecked) {
        this.ingredientChecked = ingredientChecked;
    }

    public void setImgs(int[] imgs) {
        this.imgs = imgs;
    }

    public ArrayList<String> getIngredientChecked() {
        return ingredientChecked;
    }

    public int[] getImgs() {
        return imgs;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
