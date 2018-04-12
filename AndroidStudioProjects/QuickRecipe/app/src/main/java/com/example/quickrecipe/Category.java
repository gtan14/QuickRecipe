package com.example.quickrecipe;

import java.util.ArrayList;

/**
 * Created by Gerald on 4/12/2018.
 */

public class Category {
    private String[] categories;
    private ArrayList<String> checkedList;
    private int[] imgList;

    public ArrayList<String> getCheckedList() {
        return checkedList;
    }

    public int[] getImgList() {
        return imgList;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public void setCheckedList(ArrayList<String> checkedList) {
        this.checkedList = checkedList;
    }

    public void setImgList(int[] imgList) {
        this.imgList = imgList;
    }
}
