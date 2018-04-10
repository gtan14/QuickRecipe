package com.example.quickrecipe;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.Arrays;

import jp.wasabeef.blurry.Blurry;


public class HomeFragment extends Fragment {

    private SearchView searchView;
    private GridView gridView;
    private Context context;

    public static String[] categoryList = {
            "Added Sweeteners",
            "Alcohol",
            "Condiments",
            "Dairy",
            "Fruits",
            "Grains",
            "Legumes",
            "Meats",
            "Oils",
            "Sauces",
            "Seafood",
            "Soup",
            "Spices",
            "Vegetables"
    };

    public static int[] imageList = {
            R.drawable.sweetener,
            R.drawable.alcohol,
            R.drawable.condiments,
            R.drawable.dairy,
            R.drawable.fruits,
            R.drawable.grains,
            R.drawable.legumes,
            R.drawable.meat,
            R.drawable.oil,
            R.drawable.sauces,
            R.drawable.seafood,
            R.drawable.soup,
            R.drawable.spices,
            R.drawable.vegetable
    };


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState){

        searchView = (SearchView) v.findViewById(R.id.ingredientSearchView);
        gridView = (GridView) v.findViewById(R.id.mainCategoryGridView);
    }

    @Override
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);

        //  sort by alphabetical order, so it displays correctly
        //Arrays.sort(categoryList);
        gridView.setAdapter(new MainCategoryAdapter(this, categoryList, imageList));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
