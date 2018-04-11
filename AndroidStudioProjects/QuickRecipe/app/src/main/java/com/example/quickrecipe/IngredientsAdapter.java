package com.example.quickrecipe;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Gerald on 4/10/2018.
 */

public class IngredientsAdapter extends BaseAdapter {

    private String[] ingredientList;
    private Context context;
    private TypedArray images;
    private static LayoutInflater inflater = null;

    public IngredientsAdapter(Fragment fragment, String[] ingredientList, TypedArray images){
        this.ingredientList = ingredientList;
        this.images = images;
        this.context = fragment.getContext();
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount(){
        return ingredientList.length;
    }

    @Override
    public Object getItem(int position){
        return position;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    public class Holder{
        TextView categoryName;
        ImageView categoryImage;
        ImageView ingredientCheckBox;
        boolean ingredientChecked;

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
            holder.ingredientCheckBox = (ImageView) convertView.findViewById(R.id.gridItemCheckmark);

            convertView.setTag(holder);
        }

        else{
            holder = (Holder) convertView.getTag();
        }

        holder.categoryName.setText(ingredientList[position]);
        holder.categoryImage.setImageResource(images.getResourceId(position, 0));

        if(holder.ingredientChecked){
            holder.ingredientCheckBox.setVisibility(View.VISIBLE);
        }

        else{
            holder.ingredientCheckBox.setVisibility(View.INVISIBLE);
        }

        return convertView;

    }
}
