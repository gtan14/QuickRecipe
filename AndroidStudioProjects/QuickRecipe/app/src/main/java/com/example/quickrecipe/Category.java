package com.example.quickrecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gerald on 4/12/2018.
 */

public class Category {
    private ArrayList<String> categories;
    private ArrayList<String> checkedList;
    private List<Integer> imgList;

    public ArrayList<String> getCheckedList() {
        return checkedList;
    }

    public List<Integer> getImgList() {
        return imgList;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public void setCheckedList(ArrayList<String> checkedList) {
        this.checkedList = checkedList;
    }

    public void setImgList(List<Integer> imgList) {
        this.imgList = imgList;
    }
}
