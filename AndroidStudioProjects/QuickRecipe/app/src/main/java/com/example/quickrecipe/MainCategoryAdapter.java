package com.example.quickrecipe;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Gerald on 4/9/2018.
 */

public class MainCategoryAdapter extends BaseAdapter {

    String[] categoriesList;
    Context context;
    int[] imageId;
    private static LayoutInflater inflater = null;

    public MainCategoryAdapter(HomeFragment homeFragment, String[] categoryNameList, int[] categoryImages){
        this.categoriesList = categoryNameList;
        this.imageId = categoryImages;
        this.context = homeFragment.getContext();
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        Holder holder;

        if(convertView == null){
             holder = new Holder();
             convertView = inflater.inflate(R.layout.category_layout, null);
             holder.categoryName = (TextView) convertView.findViewById(R.id.gridItemName);
             holder.categoryImage = (ImageView) convertView.findViewById(R.id.gridItemImage);
             convertView.setTag(holder);
        }

        else{
            holder = (Holder) convertView.getTag();
        }

        holder.categoryName.setText(categoriesList[position]);
        holder.categoryImage.setImageResource(imageId[position]);
        //holder.categoryImage.getBackground().setColorFilter(Color.parseColor("#80000000"), PorterDuff.Mode.SRC_ATOP);
        return convertView;

    }
}
