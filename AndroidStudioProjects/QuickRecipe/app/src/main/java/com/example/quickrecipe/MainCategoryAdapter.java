package com.example.quickrecipe;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Gerald on 4/9/2018.
 */

public class MainCategoryAdapter extends BaseAdapter {

    String[] categoriesList;
    Context context;
    FragmentActivity fragmentActivity;
    int[] imageId;

    private static LayoutInflater inflater = null;

    public MainCategoryAdapter(Fragment fragment, String[] categoryNameList, int[] categoryImages){
        this.categoriesList = categoryNameList;
        this.imageId = categoryImages;
        this.context = fragment.getContext();
        this.fragmentActivity = fragment.getActivity();
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public int getCount(){
        return categoriesList.length;
    }

    @Override
    public Object getItem(int position){
        return position;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    public class Holder{
        TextView categoryName;
        ImageView categoryImage;
        ImageView checkbox;
        boolean ingredientsChecked;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        Holder holder;

        //  if view not recycled, initialize some attr
        if(convertView == null){
             holder = new Holder();
             convertView = inflater.inflate(R.layout.category_layout, null);
             holder.categoryName = (TextView) convertView.findViewById(R.id.gridItemName);
             holder.categoryImage = (ImageView) convertView.findViewById(R.id.gridItemImage);
             holder.checkbox = (ImageView) convertView.findViewById(R.id.gridItemCheckmark);

             convertView.setTag(holder);
        }

        else{
            holder = (Holder) convertView.getTag();
        }

        holder.categoryName.setText(categoriesList[position]);
        holder.categoryImage.setImageResource(imageId[position]);
        return convertView;

    }
}
