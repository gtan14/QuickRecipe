package com.example.quickrecipe;

import android.content.res.TypedArray;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gerald on 4/10/2018.
 */

public class SubCategory implements Serializable {

    private String[] ingredientList;
    private transient TypedArray imgs;

    public SubCategory(){

    }

    public SubCategory(String[] ingredientList, TypedArray imgs){
        this.ingredientList = ingredientList;
        this.imgs = imgs;
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
