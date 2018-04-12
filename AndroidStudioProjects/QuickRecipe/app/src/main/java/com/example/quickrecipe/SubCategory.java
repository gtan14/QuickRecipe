package com.example.quickrecipe;

import android.content.res.TypedArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gerald on 4/10/2018.
 */

public class SubCategory implements Serializable {

    private int category;
    private String[] ingredientList;
    private transient TypedArray imgs;
    private ArrayList<String> ingredientChecked;

    public SubCategory(){

    }

    public SubCategory(String[] ingredientList, TypedArray imgs){
        this.ingredientList = ingredientList;
        this.imgs = imgs;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getCategory() {
        return category;
    }

    public ArrayList<String> getIngredientChecked() {
        return ingredientChecked;
    }

    public void setIngredientChecked(ArrayList<String> ingredientChecked) {
        this.ingredientChecked = ingredientChecked;
    }

    public String[] getIngredientList() {
        return ingredientList;
    }

    public TypedArray getImgs() {
        return imgs;
    }

    public void setImgs(TypedArray imgs) {
        this.imgs = imgs;
    }

    public void setIngredientList(String[] ingredientList) {
        this.ingredientList = ingredientList;
    }
}
