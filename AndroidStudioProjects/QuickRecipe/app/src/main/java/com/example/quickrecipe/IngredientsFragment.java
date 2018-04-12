package com.example.quickrecipe;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Gerald on 4/10/2018.
 */

public class IngredientsFragment extends Fragment {

    private SearchView searchView;
    private GridView gridView;
    private Context context;
    private SubCategory subCategory;
    private IngredientsAdapter ingredientsAdapter;
    private NavDrawerActivity activity;
    Bundle savedState;


    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putSerializable("saveIngredients", subCategory);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //  check if data received is null or not
        Bundle bundle = getArguments();
        if(bundle != null && bundle.containsKey("subCategory")) {
            subCategory = (SubCategory) getArguments().getSerializable("subCategory");
        }


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

        activity = (NavDrawerActivity) getActivity();

        //  checks the cart to see if any ingredients match the ingredients of the current category
        //  if so, display check mark where necessary
        for(int i = 0; i < activity.cartArrayList.size(); i++){
            for(int j = 0; j < subCategory.getIngredientList().length; j++){
                if(activity.cartArrayList.get(i).getIngredient().equals(subCategory.getIngredientList()[j])){
                    subCategory.getIngredientChecked().set(j, "1");
                }
            }
        }

        //  setup adapter
        activity.setTitle("Home");
        ingredientsAdapter = new IngredientsAdapter(this, subCategory);
        gridView.setAdapter(ingredientsAdapter);


        //  on click listener for grid view items
        //  displays check mark if item does not currently have check mark, and vice versa
        //  updates the cart quantity
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IngredientsAdapter.Holder holder = (IngredientsAdapter.Holder) view.getTag();

                if(holder.ingredientChecked.equals("0")){

                    //  updates grid view to show check mark
                    subCategory.getIngredientChecked().set(position, "1");
                    ingredientsAdapter.notifyDataSetChanged();

                    updateCartQuantity(true, holder.categoryName.getText().toString(), holder.categoryImage);
                }

                else{
                    subCategory.getIngredientChecked().set(position, "0");
                    ingredientsAdapter.notifyDataSetChanged();

                    updateCartQuantity(false, holder.categoryName.getText().toString(), null);
                }
            }
        });
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

    //  method that updates cart quantity when ingredient is selected and adds ingredient and image to cart
    private void updateCartQuantity(boolean increment, String ingredient, ImageView ingredientImg){
        String quantityString = activity.cartQuantity.getText().toString();
        int quantityInt = Integer.parseInt(quantityString);

        if(increment) {
            quantityInt++;

            //  add new cart item
            Cart cart = new Cart(subCategory.getCategory(), ingredient, ingredientImg);
            activity.cartArrayList.add(cart);
        }
        else{
            quantityInt--;

            //  checks the cart array list to see if the ingredient that is unchecked is contained in the array list
            //  if it is, it is removed from the array list
            for(int i = 0; i < activity.cartArrayList.size(); i++){
                if(activity.cartArrayList.get(i).getIngredient().equals(ingredient)){
                    activity.cartArrayList.remove(i);
                }
            }
        }

        activity.cartQuantity.setText(String.format("%s", quantityInt));
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        //savedState.putSerializable("saveIngredients", subCategory);
    }

}
